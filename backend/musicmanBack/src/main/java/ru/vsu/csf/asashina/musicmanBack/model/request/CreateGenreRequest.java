package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateGenreRequest {

    @NotBlank
    @Size(max = 100)
    private String name;
}
