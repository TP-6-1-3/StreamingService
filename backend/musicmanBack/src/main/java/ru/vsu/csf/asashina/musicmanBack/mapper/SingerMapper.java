package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SingerDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Singer;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSingerRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdateSingerRequest;

@Mapper
public interface SingerMapper {

    SingerMapper INSTANCE = Mappers.getMapper(SingerMapper.class);

    SingerDTO toDTOFromEntity(Singer entity);

    Singer toEntityFromDTO(SingerDTO dto);

    Singer toEntityFromRequest(CreateSingerRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateSingerRequest request, @MappingTarget Singer entity);
}
