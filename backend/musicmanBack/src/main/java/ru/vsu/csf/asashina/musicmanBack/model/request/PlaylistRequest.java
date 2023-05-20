package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PlaylistRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    private String description;
}
