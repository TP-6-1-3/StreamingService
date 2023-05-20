package ru.vsu.csf.asashina.musicmanBack.model.dto.page;

import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.PagingInfoDTO;

import java.util.List;

public class SongPagesDTO extends PagingDTO<SongDTO> {
    public SongPagesDTO(PagingInfoDTO paging, List<SongDTO> data) {
        super(paging, data);
    }
}
