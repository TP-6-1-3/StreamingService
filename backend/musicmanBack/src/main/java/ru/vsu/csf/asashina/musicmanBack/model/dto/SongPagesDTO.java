package ru.vsu.csf.asashina.musicmanBack.model.dto;

import java.util.List;

public class SongPagesDTO extends PagingDTO<SongPageDTO> {
    public SongPagesDTO(PagingInfoDTO paging, List<SongPageDTO> data) {
        super(paging, data);
    }
}
