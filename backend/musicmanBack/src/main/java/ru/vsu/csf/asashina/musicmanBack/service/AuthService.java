package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.exception.WrongCredentialsException;
import ru.vsu.csf.asashina.musicmanBack.model.dto.TokensDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.VerificationEmailDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.LoginRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;
import ru.vsu.csf.asashina.musicmanBack.template.EmailTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final VerificationService verificationService;
    private final EmailService emailService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Value("${emailTemplate.mainPage}")
    private String mainPage;

    @Value("${emailTemplate.verificationLink}")
    private String verificationLink;

    @Transactional
    public TokensDTO signUp(UserSignUpRequest request) {
        UserDTO registeredUser = userService.registerUser(request);
        UUID code = verificationService.createCodeAndSave(registeredUser);
        sendCode(registeredUser, code);
        return tokenService.createTokens(registeredUser);
    }

    @Async
    private void sendCode(UserDTO user, UUID code) {
        emailService.sendTemplate(
                user.getEmail(),
                "Подтверждение регистрации на платформе Musicman",
                EmailTemplate.VERIFICATION_USER,
                new VerificationEmailDTO(
                        user.getFirstName() + " " + user.getLastName(),
                        mainPage,
                        verificationLink + "/" + code.toString()
                ));
    }

    @Transactional
    public void resendCode(UserDTO user) {
        UUID code = verificationService.resendCode(user);
        sendCode(user, code);
    }

    @Transactional
    public void verifyUser(String code) {
        Long userId = verificationService.getVerificationUserId(code);
        userService.verifyUserById(userId);
    }

    @Transactional
    public TokensDTO login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new WrongCredentialsException("Неправильный логин (почта) или пароль");
        }
        UserDTO user = userService.getUserByEmail(request.getEmail());
        return tokenService.createTokens(user);
    }
}
