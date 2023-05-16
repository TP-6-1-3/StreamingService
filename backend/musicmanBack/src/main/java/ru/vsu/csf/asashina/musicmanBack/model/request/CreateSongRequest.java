package ru.vsu.csf.asashina.musicmanBack.model.request;

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

    @NotBlank
    @Size(max = 255)
    private String title;
    @DateTimeFormat(pattern = "mm:ss")
    @NotNull
    private LocalTime duration;
    @NotNull
    private Long singerId;
    private List<Long> genreIds;

    private MultipartFile file;
}
