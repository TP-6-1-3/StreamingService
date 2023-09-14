package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SongDTO {

    private Long songId;
    private String title;
    private String createdYear;
    private String album;
    private SingerDTO singer;
    private Set<GenreDTO> genres;
}
