package ru.vsu.csf.asashina.musicmanBack.model.dto.page;

import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;

import java.util.List;

public class SingerPagesDTO extends PagingDTO<SingerDTO> {

    public SingerPagesDTO(PagingInfoDTO paging, List<SingerDTO> data) {
        super(paging, data);
    }
}
