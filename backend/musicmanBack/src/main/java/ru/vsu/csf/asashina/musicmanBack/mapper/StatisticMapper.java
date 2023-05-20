package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Statistic;

@Mapper(uses = { UserMapper.class, GenreMapper.class })
public interface StatisticMapper {

    StatisticMapper INSTANCE = Mappers.getMapper(StatisticMapper.class);

    @Mapping(target = "amount", expression = "java(0L)")
    Statistic createStatistic(UserDTO user, GenreDTO genre);
}
