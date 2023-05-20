package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpRequest {

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String lastName;

    @Email(message = "Почта указана неверно")
    @NotBlank(message = "Почта не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Никнейм не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Никнейм не может быть больше 100 символов")
    private String nickname;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String password;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Повторить Пароль не может быть пустым")
    @Size(min = 8, max = 20, message = "Повторить Пароль не может быть меньше 8 и больше 20 символов")
    private String repeatPassword;
}
