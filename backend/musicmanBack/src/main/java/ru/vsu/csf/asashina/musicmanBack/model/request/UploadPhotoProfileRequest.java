package ru.vsu.csf.asashina.musicmanBack.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPhotoProfileRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile file;
}
