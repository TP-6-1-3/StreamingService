package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSingerRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Полное имя не может быть пустым")
    @Size(max = 255, message = "Полное имя не должно превышать 255 символов")
    private String fullName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;
}
