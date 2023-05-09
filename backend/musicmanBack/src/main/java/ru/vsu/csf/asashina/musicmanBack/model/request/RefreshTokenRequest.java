package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;
}
