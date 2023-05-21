package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticUserDTO {

    private Long userId;
    private List<StatisticGenreDTO> genreStatistics;
    private Long totalSongsAmount;
}
