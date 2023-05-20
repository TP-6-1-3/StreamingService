package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.csf.asashina.musicmanBack.annotation.NullOrNotBlank;

@Data
@AllArgsConstructor
public class UpdatePlaylistRequest {

    @Size(max = 100)
    @NullOrNotBlank
    private String name;

    @NullOrNotBlank
    private String description;
}
