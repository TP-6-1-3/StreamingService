package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Genre;

import java.util.List;
import java.util.Set;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDTO toDTOFromEntity(Genre entity);

    Genre toEntityFromDTO(GenreDTO dto);

    Set<GenreDTO> toDTOFromEntitySet(Set<Genre> entities);

    List<GenreDTO> toDTOFromEntityList(List<Genre> entities);

    Set<Genre> toEntityFromDTOSet(Set<GenreDTO> dtos);
}
