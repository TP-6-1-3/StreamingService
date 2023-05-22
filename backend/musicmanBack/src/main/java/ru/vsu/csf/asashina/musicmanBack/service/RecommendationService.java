package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoFriendException;
import ru.vsu.csf.asashina.musicmanBack.mapper.RecommendationMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Recommendation;
import ru.vsu.csf.asashina.musicmanBack.repository.RecommendationRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final RecommendationMapper recommendationMapper;
    private final SongMapper songMapper;

    private final SongService songService;
    private final FriendService friendService;
    private final UserService userService;

    @Value("${songs.daysToRecommend}")
    private Integer daysToRecommend;

    @Transactional
    public List<SongDTO> getRecommendedSongs(Long userId) {
        var recommendations =  recommendationRepository.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(recommendations)) {
            return Collections.emptyList();
        }
        return recommendations.stream()
                .map(recommendation -> songMapper.toDTOFromEntity(recommendation.getSong()))
                .toList();
    }

    @Transactional
    public void recommendSongToUser(UserDTO user, Long songId, String nickname) {
        SongDTO song = songService.getSongById(songId);
        UserDTO friend = userService.getUserByNickname(nickname);
        if (!friendService.hasFriend(user, friend)) {
            throw new NoFriendException("Пользователя нет в друзьях");
        }
        if (recommendationRepository.findByUserIdAndSongId(user.getUserId(), songId).isPresent()) {
            throw new EntityAlreadyExistsException("Песня уже рекомендована пользователю");
        }
        recommendationRepository.save(recommendationMapper.createEntity(
                song, friend, Instant.now().plusSeconds(daysToRecommend * 3600 * 24)));
    }

    @Async
    @Transactional
    public void deleteFromRecommendation(Long userId, Long songId) {
        Optional<Recommendation> recommendation = recommendationRepository.findByUserIdAndSongId(userId, songId);
        if (recommendation.isEmpty()) {
            return;
        }
        recommendationRepository.delete(recommendation.get());
    }

    @Transactional
    public void deleteOldRecommendations() {
        recommendationRepository.deleteOld(Instant.now());
    }
}