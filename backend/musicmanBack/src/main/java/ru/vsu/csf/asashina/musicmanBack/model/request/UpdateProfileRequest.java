package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.asashina.musicmanBack.annotation.NullOrNotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    @NullOrNotBlank(message = "Указанное имя не должно быть пустым")
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @NullOrNotBlank(message = "Указанная фамилия не должно быть пустым")
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String lastName;

    @NullOrNotBlank(message = "Указанный никнейм не должно быть пустым")
    @Size(max = 100, message = "Никнейм не может быть больше 100 символов")
    private String nickname;
}
