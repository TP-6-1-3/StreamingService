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
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Recommendation;
import ru.vsu.csf.asashina.musicmanBack.repository.RecommendationRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final RecommendationMapper recommendationMapper;
    private final SongMapper songMapper;

    private final SongService songService;
    private final GenreService genreService;
    private final FriendService friendService;
    private final UserService userService;
    private final StatisticService statisticService;

    @Value("${songs.daysToRecommend}")
    private Integer daysToRecommend;

    @Transactional
    public List<SongDTO> getRecommendedSongs(Long userId) {
        var recommendations =  recommendationRepository.findAllByUserId(userId);
        List<SongDTO> recommendedSongsFromFriends = new ArrayList<>();
        if (!CollectionUtils.isEmpty(recommendations)) {
            recommendedSongsFromFriends = recommendations.stream()
                    .map(recommendation -> songMapper.toDTOFromEntity(recommendation.getSong()))
                    .limit(10)
                    .toList();
        }
        var size = 20 - recommendedSongsFromFriends.size();
        var statistics = statisticService.getUserStatistic(userId);
        List<Long> topGenres;
        if (statistics.getGenreStatistics().isEmpty()) {
            topGenres = genreService.getAll().stream()
                    .map(GenreDTO::getGenreId)
                    .limit(5)
                    .toList();
        } else {
            topGenres = statistics.getGenreStatistics().stream()
                    .map(statistic -> statistic.getGenre().getGenreId())
                    .limit(5)
                    .collect(Collectors.toList());
            if (topGenres.size() < 5) {
                topGenres.addAll(
                        genreService.getAll().stream()
                                .map(GenreDTO::getGenreId)
                                .filter(id -> !topGenres.contains(id))
                                .limit(5 - topGenres.size())
                                .toList());
            }
        }
        var randomSongs = songService.getRecommendedSongs(userId, topGenres)
                .stream()
                .limit(size)
                .collect(Collectors.toList());
        randomSongs.addAll(recommendedSongsFromFriends);
        return randomSongs;
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
