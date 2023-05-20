package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Playlist;

@Mapper(uses = SongMapper.class)
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    PlaylistDTO toDTOFromEntity(Playlist entity);

    PlaylistWithSongsDTO toDTOWithSongFromEntity(Playlist entity);
}
