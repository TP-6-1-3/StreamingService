package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSingerRequest {

    @NotBlank(message = "Описание не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
