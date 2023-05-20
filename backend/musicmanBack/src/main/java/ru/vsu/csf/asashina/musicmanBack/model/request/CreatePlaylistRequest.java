package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePlaylistRequest {

    @NotBlank(message = "Название не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Название не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
