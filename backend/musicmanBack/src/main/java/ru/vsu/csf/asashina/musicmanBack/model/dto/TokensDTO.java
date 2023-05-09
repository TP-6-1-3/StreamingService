package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class TokensDTO {

    private String accessToken;
    private String refreshToken;
}
