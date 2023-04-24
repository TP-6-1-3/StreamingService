package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.VerificationExpiredException;
import ru.vsu.csf.asashina.musicmanBack.mapper.VerificationMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Verification;
import ru.vsu.csf.asashina.musicmanBack.repository.VerificationRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.UuidUtil;

import java.time.Instant;

@Service
@AllArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;

    private final VerificationMapper verificationMapper;

    @Transactional
    public String createCodeAndSave(UserDTO user) {
        String code = UuidUtil.generateRandomUUIDInString();
        verificationRepository.save(
                verificationMapper.toEntityFromParams(
                        UuidUtil.generateRandomUUIDInString(), user, Instant.now().plusSeconds(3600), code));
        return code;
    }

    @Transactional
    public Long getVerificationUserId(String code) {
        Verification verification = verificationRepository.findByCode(code).orElseThrow(
                () -> new EntityDoesNotExistException("Данного кода для верификации не существует")
        );
        if (verification.getValidTill().isBefore(Instant.now())) {
            throw new VerificationExpiredException("Истекло время действия кода");
        }
        verificationRepository.delete(verification);
        return verification.getUser().getUserId();
    }
}
