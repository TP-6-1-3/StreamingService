package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Role;

import java.util.Set;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTOFromEntity(Role entity);

    Set<RoleDTO> toDTOFromEntitySet(Set<Role> entities);

    Set<Role> toEntityFromDTOSet(Set<RoleDTO> dtos);
}
