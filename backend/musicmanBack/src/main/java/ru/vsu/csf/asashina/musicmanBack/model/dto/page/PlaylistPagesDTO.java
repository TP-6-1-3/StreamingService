package ru.vsu.csf.asashina.musicmanBack.model.dto.page;

import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.PagingInfoDTO;

import java.util.List;

public class PlaylistPagesDTO extends PagingDTO<PlaylistDTO> {

    public PlaylistPagesDTO(PagingInfoDTO paging, List<PlaylistDTO> data) {
        super(paging, data);
    }
}
