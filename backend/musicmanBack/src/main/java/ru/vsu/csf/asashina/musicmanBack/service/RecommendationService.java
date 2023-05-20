package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoFriendException;
import ru.vsu.csf.asashina.musicmanBack.mapper.RecommendationMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.SongMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.repository.RecommendationRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final RecommendationMapper recommendationMapper;
    private final SongMapper songMapper;

    private final SongService songService;
    private final FriendService friendService;

    @Value("${songs.daysToRecommend}")
    private Integer daysToRecommend;

    public List<SongDTO> getRecommendedSongs(Long userId) {
        return recommendationRepository.findAllByUserId(userId)
                .stream()
                .map(recommendation -> songMapper.toDTOFromEntity(recommendation.getSong()))
                .toList();
    }

    @Transactional
    public void recommendSongToUser(UserDTO user, Long songId, String nickname) {
        SongDTO song = songService.getSongById(songId);
        if (!friendService.hasFriend(user, nickname)) {
            throw new NoFriendException("Пользователя нет в друзьях");
        }
        if (recommendationRepository.findByUserIdAndSongId(user.getUserId(), songId).isPresent()) {
            throw new EntityAlreadyExistsException("Песня уже рекомендована пользователю");
        }
        recommendationRepository.save(recommendationMapper.createEntity(
                song, user, Instant.now().plusSeconds(daysToRecommend * 3600 * 24)));
    }

    @Async
    @Transactional
    public void deleteFromRecommendation(Long userId, Long songId) {
        recommendationRepository.deleteByUserIdAndSongId(userId, songId);
    }

    @Transactional
    public void deleteOldRecommendations() {
        recommendationRepository.deleteOld(Instant.now());
    }
}
