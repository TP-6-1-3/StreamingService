package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpRequest {

    @NotBlank(message = "Поле Имя не может быть пустым")
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @NotBlank(message = "Поле Фамилия не может быть пустым")
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String secondName;

    @NotBlank(message = "Поле Почта не может быть пустым")
    @Email(message = "Почта указана неверно")
    private String email;

    @NotBlank(message = "Поле Никнейм не может быть пустым")
    @Size(max = 100, message = "Никнейм не может быть больше 100 символов")
    private String nickname;

    @NotBlank(message = "Поле Пароль не может быть пустым")
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String password;

    @NotBlank(message = "Поле Повторить Пароль не может быть пустым")
    @Size(min = 8, max = 20, message = "Повторить Пароль не может быть меньше 8 и больше 20 символов")
    private String repeatPassword;
}
