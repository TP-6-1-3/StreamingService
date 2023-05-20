package ru.vsu.csf.asashina.musicmanBack.model.dto;

import java.util.List;

public class SingerPagesDTO extends PagingDTO<SingerDTO> {

    public SingerPagesDTO(PagingInfoDTO paging, List<SingerDTO> data) {
        super(paging, data);
    }
}
