package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSongRequest;

import java.time.LocalTime;
import java.util.Set;

@Mapper(uses = { GenreMapper.class, SingerMapper.class })
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "duration", source = "duration")
    Song toEntityFromRequest(CreateSongRequest request, LocalTime duration, SingerDTO singer, Set<GenreDTO> genres);

    Song toEntityFromDTO(SongDTO dto);

    SongDTO toDTOFromEntity(Song entity);

    Set<SongDTO> toDTOFromEntitySet(Set<Song> entities);
}
