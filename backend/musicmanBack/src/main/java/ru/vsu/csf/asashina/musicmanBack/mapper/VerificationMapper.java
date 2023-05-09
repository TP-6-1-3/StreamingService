package ru.vsu.csf.asashina.musicmanBack.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Verification;

import java.time.Instant;

@Mapper(uses = UserMapper.class)
public interface VerificationMapper {

    VerificationMapper INSTANCE = Mappers.getMapper(VerificationMapper.class);

    Verification toEntityFromParams(String verificationId, UserDTO user, Instant validTill, String code);
}
