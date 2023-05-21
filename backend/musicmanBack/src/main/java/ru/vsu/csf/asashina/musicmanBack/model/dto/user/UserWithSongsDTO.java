package ru.vsu.csf.asashina.musicmanBack.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;

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
}
