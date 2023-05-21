package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class SongDTO {

    private Long songId;
    private String title;
    private LocalTime duration;
    private SingerDTO singer;
    private Set<GenreDTO> genres;
}
