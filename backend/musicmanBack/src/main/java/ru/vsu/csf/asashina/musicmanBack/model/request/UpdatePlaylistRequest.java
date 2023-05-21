package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.asashina.musicmanBack.annotation.NullOrNotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlaylistRequest {

    @NullOrNotBlank(message = "Указанное имя не должно быть пустым")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    private String name;

    @NullOrNotBlank(message = "Указанное описание не должно быть пустым")
    private String description;
}
