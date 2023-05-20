package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;

import java.util.Set;

@Mapper(uses = { RoleMapper.class, SongMapper.class })
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromEntity(User entity);

    User toEntityFromDTO(UserDTO dto);

    @Mapping(target = "isVerified", expression = "java(false)")
    User toEntityFromRequest(UserSignUpRequest request, Set<RoleDTO> roles, String passwordHash);

    User toEntityFromDTO(UserWithSongsDTO dto);

    UserWithSongsDTO toWithSongsDTOFromEntity(User entity);

    FriendDTO toFriendDTOFromEntity(User entity);
}
