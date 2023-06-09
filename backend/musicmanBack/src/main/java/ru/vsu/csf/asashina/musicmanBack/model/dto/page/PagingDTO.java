package ru.vsu.csf.asashina.musicmanBack.model.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagingDTO<T> {

    private PagingInfoDTO paging;
    private List<T> data;
}
