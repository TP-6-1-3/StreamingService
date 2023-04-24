package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.*;

@Data
public class TokensDTO {

    private String accessToken;
    private String refreshToken;
}
