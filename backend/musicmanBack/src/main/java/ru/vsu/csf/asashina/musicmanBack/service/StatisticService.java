package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.mapper.GenreMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.StatisticMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.StatisticGenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.StatisticUserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Statistic;
import ru.vsu.csf.asashina.musicmanBack.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    private final StatisticMapper statisticMapper;
    private final GenreMapper genreMapper;

    private final UserService userService;
    private final GenreService genreService;

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
                statistics.add(statisticMapper.createStatistic(user, findGenre(genres, genreId)));
            }
        }
        statistics.forEach(Statistic::incrementAmount);
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

    public StatisticUserDTO getUserStatistic(Long userId) {
        userService.getUserById(userId);
        List<Statistic> statistics = statisticRepository.findByUserId(userId);
        StatisticUserDTO result = new StatisticUserDTO();
        result.setUserId(userId);

        long totalAmount = 0L;
        List<StatisticGenreDTO> statisticGenres = new ArrayList<>();
        for (Statistic statistic : statistics) {
            statisticGenres.add(new StatisticGenreDTO(
                    genreMapper.toDTOFromEntity(statistic.getGenre()),
                    statistic.getAmount()));
            totalAmount += statistic.getAmount();
        }
        result.setGenreStatistics(statisticGenres);
        result.setTotalSongsAmount(totalAmount);
        return result;
    }

    public StatisticGenreDTO getGenreStatistic(Long genreId) {
        GenreDTO genre = genreService.getGenreById(genreId);
        List<Statistic> statistics = statisticRepository.findByGenreId(genreId);
        StatisticGenreDTO result = new StatisticGenreDTO();
        result.setGenre(genre);
        result.setAmount(statistics.stream()
                .map(Statistic::getAmount)
                .mapToLong(Long::longValue)
                .sum());
        return result;
    }
}
