package ru.vsu.csf.asashina.musicmanBack.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;

import java.util.Set;

@Data
@AllArgsConstructor
public class CredentialsDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String nickname;
    private Boolean isVerified;
    private Set<RoleDTO> roles;
}
