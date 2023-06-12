package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.mapper.RoleMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Role;
import ru.vsu.csf.asashina.musicmanBack.repository.RoleRepository;

import java.util.Set;

import static ru.vsu.csf.asashina.musicmanBack.model.constant.Role.ADMIN;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Role.USER;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDTO getUserRole() {
        Role role = roleRepository.findByName(USER).orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return roleMapper.toDTOFromEntity(role);
    }

    public boolean isAdmin(Set<RoleDTO> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ADMIN));
    }
}
