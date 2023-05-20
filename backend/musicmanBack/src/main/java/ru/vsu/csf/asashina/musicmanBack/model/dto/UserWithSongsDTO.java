package ru.vsu.csf.asashina.musicmanBack.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserWithSongsDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String email;
    private String nickname;
    private Boolean isVerified;
    private Set<RoleDTO> roles;
    private Set<SongDTO> songs;

    public void addSong(SongDTO song) {
        songs.add(song);
    }

    public void deleteSong(SongDTO song) {
        songs.remove(song);
    }
}
