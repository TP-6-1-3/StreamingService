package ru.vsu.csf.asashina.musicmanBack.model.dto.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class UserDTO implements UserDetails {

    private Long userId;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String email;
    private String nickname;
    private Boolean isVerified;
    private Set<RoleDTO> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }
}
