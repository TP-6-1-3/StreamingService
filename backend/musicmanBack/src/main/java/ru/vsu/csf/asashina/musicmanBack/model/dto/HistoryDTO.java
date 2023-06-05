package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HistoryDTO {

    private List<SongDTO> songs;
}
