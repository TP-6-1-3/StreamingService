package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
