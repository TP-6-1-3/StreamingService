package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGenresToSongRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ИД жанров не могут быть пустыми")
    List<Long> genresIds;
}
