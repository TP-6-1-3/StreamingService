package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Song;

import java.util.Set;

@Mapper(uses = GenreMapper.class)
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "singer", expression = "java(entity.getSinger().getFullName())")
    SongPageDTO toPageDTOFromEntity(Song entity);
}
