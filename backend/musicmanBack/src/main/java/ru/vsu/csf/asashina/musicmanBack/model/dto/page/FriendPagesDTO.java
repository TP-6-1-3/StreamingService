package ru.vsu.csf.asashina.musicmanBack.model.dto.page;

import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;

import java.util.List;

public class FriendPagesDTO extends PagingDTO<FriendDTO> {

    public FriendPagesDTO(PagingInfoDTO paging, List<FriendDTO> data) {
        super(paging, data);
    }
}
