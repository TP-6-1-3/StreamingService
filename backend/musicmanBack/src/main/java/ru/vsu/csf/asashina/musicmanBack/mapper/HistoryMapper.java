package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.History;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = { UserMapper.class, SongMapper.class })
public interface HistoryMapper {

    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    History createEntity(UserDTO user, SongDTO song, Instant playTimestamp);
}
