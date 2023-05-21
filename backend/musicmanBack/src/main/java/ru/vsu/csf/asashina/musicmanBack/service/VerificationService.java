package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.AlreadyVerifiedUserException;
import ru.vsu.csf.asashina.musicmanBack.exception.VerificationExpiredException;
import ru.vsu.csf.asashina.musicmanBack.mapper.VerificationMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.Verification;
import ru.vsu.csf.asashina.musicmanBack.repository.VerificationRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;

    private final VerificationMapper verificationMapper;

    @Transactional
    public UUID createCodeAndSave(UserDTO user) {
        UUID code = UUID.randomUUID();
        verificationRepository.save(
                verificationMapper.toEntityFromParams(user, Instant.now().plusSeconds(3600), code));
        return code;
    }

    @Transactional
    public Long getVerificationUserId(String code) {
        Verification verification = verificationRepository.findByCode(UUID.fromString(code)).orElseThrow(
                () -> new EntityDoesNotExistException("Данного кода для верификации не существует")
        );
        if (verification.getValidTill().isBefore(Instant.now())) {
            throw new VerificationExpiredException("Истекло время действия кода");
        }
        verificationRepository.delete(verification);
        return verification.getUser().getUserId();
    }

    public UUID resendCode(UserDTO user) {
        if (user.getIsVerified()) {
            throw new AlreadyVerifiedUserException("Пользователь уже верифицирован");
        }
        return createCodeAndSave(user);
    }
}
