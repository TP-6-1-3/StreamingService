package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.VerificationEmailDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;
import ru.vsu.csf.asashina.musicmanBack.template.EmailTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final VerificationService verificationService;
    private final EmailService emailService;

    @Value("${emailTemplate.mainPage}")
    private String mainPage;

    @Transactional
    public void signUp(UserSignUpRequest request, String verificationLink) {
        UserDTO registeredUser = userService.registerUser(request);
        String code = verificationService.createCodeAndSave(registeredUser);
        emailService.sendTemplate(
                registeredUser.getEmail(),
                "Подтверждение регистрации на платформе Musicman",
                EmailTemplate.VERIFICATION_USER,
                new VerificationEmailDTO(
                        registeredUser.getFirstName() + " " + registeredUser.getLastName(),
                        mainPage,
                        verificationLink + "/" + code
                ));
    }

    @Transactional
    public void verifyUser(String code) {
        Long userId = verificationService.getVerificationUserId(code);
        userService.verifyUserById(userId);
    }
}
