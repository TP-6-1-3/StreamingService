package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class PlaylistWithSongsDTO {

    private PlaylistDTO playlistInfo;
    private Set<SongDTO> songs;
}
