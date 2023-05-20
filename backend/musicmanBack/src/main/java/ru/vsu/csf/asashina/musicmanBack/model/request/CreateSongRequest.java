package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateSongRequest {

    @NotBlank(message = "Название не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 255, message = "Название не может превышать 255 символов")
    private String title;

    @DateTimeFormat(pattern = "mm:ss")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Длительность не может быть пустой")
    private LocalTime duration;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ИД исполнителя не может быть пустым")
    private Long singerId;

    private List<Long> genreIds;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile file;
}
