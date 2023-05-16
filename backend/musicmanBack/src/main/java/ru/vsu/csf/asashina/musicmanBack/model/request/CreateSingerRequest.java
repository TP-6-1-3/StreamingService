package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSingerRequest {

    @NotBlank
    @Size(max = 255)
    private String fullName;

    @NotBlank
    private String description;
}
