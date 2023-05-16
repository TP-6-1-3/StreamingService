package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;

import java.util.Set;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromEntity(User entity);

    @Mapping(target = "roles", expression = "java(roleMapper.toEntityFromDTOSet(dto.getRoles()))")
    User toEntityFromDTO(UserDTO dto);

    @Mappings(value = {
            @Mapping(target = "roles", expression = "java(roleMapper.toEntityFromDTOSet(roles))"),
            @Mapping(target = "isVerified", expression = "java(false)")
    })
    User toEntityFromRequest(UserSignUpRequest request, Set<RoleDTO> roles, String passwordHash);
}