package ru.vsu.csf.asashina.musicmanBack.model.dto;

import java.util.List;

public class PlaylistPagesDTO extends PagingDTO<PlaylistDTO>{

    public PlaylistPagesDTO(PagingInfoDTO paging, List<PlaylistDTO> data) {
        super(paging, data);
    }
}
