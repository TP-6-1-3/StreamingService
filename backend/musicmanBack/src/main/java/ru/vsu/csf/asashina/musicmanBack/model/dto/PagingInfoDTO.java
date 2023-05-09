package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class PagingInfoDTO {

    private Integer pageNumber;
    private Integer size;
    private Integer totalPages;
}
