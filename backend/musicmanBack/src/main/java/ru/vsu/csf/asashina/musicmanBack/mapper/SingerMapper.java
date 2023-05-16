package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Singer;

@Mapper
public interface SingerMapper {

    SingerMapper INSTANCE = Mappers.getMapper(SingerMapper.class);

    SingerDTO toDTOFromEntity(Singer entity);

    Singer toEntityFromDTO(SingerDTO dto);
}
