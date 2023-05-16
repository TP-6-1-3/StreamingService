package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SingerMapper {

    SingerMapper INSTANCE = Mappers.getMapper(SingerMapper.class);
}
