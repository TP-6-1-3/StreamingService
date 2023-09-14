package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.TokensDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.CredentialsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.*;
import ru.vsu.csf.asashina.musicmanBack.service.AuthService;
import ru.vsu.csf.asashina.musicmanBack.service.TokenService;
import ru.vsu.csf.asashina.musicmanBack.service.UserService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.AUTH;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/credentials")
    @Operation(summary = "Получение информации о пользователе", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Получение кредов", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CredentialsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getCredentials(Authentication authentication) {
        UserDTO user = userService.getUserByEmail((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, userService.getCredentials(user));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с указанной почтой или указанным ником уже существует",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
                    })
    })
    @SecurityRequirements
    public ResponseEntity<?> signUpNewUserUsingForm(@RequestBody @Valid UserSignUpRequest request) {
        return ResponseBuilder.build(CREATED, authService.signUp(request));
    }

    @PostMapping("/verify/{code}")
    @Operation(summary = "Верификация пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь верифицирован", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(
                    responseCode = "404", description = "Ссылка невалидна", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(
                    responseCode = "405", description = "Истекло время действия ссылки", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> verifyUser(@PathVariable("code") String code) {
        authService.verifyUser(code);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }

    @PostMapping("/resend-code")
    @Operation(summary = "Повторная отправка кода", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Код повторно отправлен", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "405", description = "Пользователь уже верифицирован", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> resendCode(Authentication authentication) {
        UserDTO user = userService.getUserByEmail((String) authentication.getPrincipal());
        authService.resendCode(user);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }


    @PostMapping("/login")
    @Operation(summary = "Вход пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно вошел в систему", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Неправильный пароль или почта", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest request) {
        return ResponseBuilder.build(OK, authService.login(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Обновление access токена", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращение обновленных токенов", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokensDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Токен отсутствует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Токен не валиден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Токен не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseBuilder.build(OK, tokenService.refreshAccessToken(request));
    }

    @PutMapping("/profile")
    @Operation(summary = "Обновление информации о пользователе", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409",
                    description = "Пользователь c указанным ником уже существует",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
                    })
    })
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UpdateProfileRequest request,
                                           Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        userService.updateProfile(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя", tags = AUTH, responses = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

    @GetMapping("/{nickname}/photo")
    @Operation(summary = "Получение фотографии пользователя по никнейму", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Фотография получена", content = {
                    @Content(mediaType = IMAGE_PNG_VALUE)
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя или фотографии не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public void getUsersPhoto(@PathVariable("nickname") String nickname, HttpServletResponse response)
            throws IOException {
        File file = userService.getFileFromSystem(nickname);
        response.setContentType(IMAGE_PNG_VALUE);
        response.setContentLength((int) Files.size(file.toPath()));
        Files.copy(file.toPath(), response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping(value = "/profile/photo", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка фотографии пользователя в профиль", tags = AUTH, responses = {
            @ApiResponse(responseCode = "200", description = "Фотография загружена"),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> uploadPhotoProfile(@ModelAttribute @Valid UploadPhotoProfileRequest request,
                                   Authentication authentication) throws IOException {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        userService.uploadProfilePicture(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

}
