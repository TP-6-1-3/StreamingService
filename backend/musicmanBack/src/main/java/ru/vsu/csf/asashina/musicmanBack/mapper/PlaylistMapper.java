package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdatePlaylistRequest;

@Mapper(uses = { SongMapper.class, UserMapper.class })
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    PlaylistDTO toDTOFromEntity(Playlist entity);

    @Mapping(target = "playlistInfo", expression = "java(toDTOFromEntity(entity))")
    PlaylistWithSongsDTO toDTOWithSongFromEntity(Playlist entity);

    Playlist toEntityFromRequest(CreatePlaylistRequest request, UserDTO user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdatePlaylistRequest request, @MappingTarget Playlist entity);
}
