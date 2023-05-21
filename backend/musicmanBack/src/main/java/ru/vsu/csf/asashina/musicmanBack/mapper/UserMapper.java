package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.RoleDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.CredentialsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdateProfileRequest;
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

    CredentialsDTO toCredentialsDTOFromDTO(UserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfileRequest request, @MappingTarget User entity);
}
