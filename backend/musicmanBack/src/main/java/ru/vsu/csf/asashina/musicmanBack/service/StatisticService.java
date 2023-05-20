package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.mapper.StatisticMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Statistic;
import ru.vsu.csf.asashina.musicmanBack.repository.StatisticRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.UuidUtil;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    private final StatisticMapper statisticMapper;

    @Async
    @Transactional
    public void updateStatistic(UserDTO user, Set<GenreDTO> genres) {
        List<Long> genreIds = genres.stream().map(GenreDTO::getGenreId).toList();
        List<Statistic> statistics = statisticRepository.findByUserIdAndGenreId(user.getUserId(), genreIds);
        List<Long> genreIdFromExistingStatistics = statistics.stream()
                .map(statistic -> statistic.getGenre().getGenreId())
                .toList();
        for (Long genreId : genreIds) {
            if (!genreIdFromExistingStatistics.contains(genreId)) {
                statistics.add(statisticMapper.createStatistic(
                        UuidUtil.generateRandomUUIDInString(), user, findGenre(genres, genreId)));
            }
        }
        statisticRepository.saveAll(statistics);
    }

    private GenreDTO findGenre (Set<GenreDTO> genres, Long genreId) {
        GenreDTO genreDTO = null;
        for (GenreDTO genre : genres) {
            if (genre.getGenreId().equals(genreId)) {
                return genre;
            }
            genreDTO = genre;
        }
        return genreDTO;
    }
}
