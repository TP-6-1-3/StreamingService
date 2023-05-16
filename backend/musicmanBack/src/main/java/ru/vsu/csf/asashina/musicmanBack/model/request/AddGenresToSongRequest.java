package ru.vsu.csf.asashina.musicmanBack.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddGenresToSongRequest {

    @NotNull
    List<Long> genresIds;
}
