package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.TokensDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.LoginRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.RefreshTokenRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;
import ru.vsu.csf.asashina.musicmanBack.service.AuthService;
import ru.vsu.csf.asashina.musicmanBack.service.TokenService;
import ru.vsu.csf.asashina.musicmanBack.service.UserService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.AUTH;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final UserService userService;

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
    public ResponseEntity<?> signUpNewUserUsingForm(@RequestBody @Valid UserSignUpRequest request,
                                                    HttpServletRequest req) {
        return ResponseBuilder.build(
                CREATED,
                authService.signUp(request, req.getRequestURL().toString().replace("register", "verify")));
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
            @ApiResponse(
                    responseCode = "404", description = "Пользователь не найден", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(
                    responseCode = "405", description = "Пользователь уже верифицирован", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> resendCode(Authentication authentication, HttpServletRequest request) {
        UserDTO user = userService.getUserByEmail((String) authentication.getPrincipal());
        authService.resendCode(user, request.getRequestURL().toString().replace("resend-code", "verify"));
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
            @ApiResponse(responseCode = "401", description = "Refresh token is expired", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Refresh token does not exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseBuilder.build(OK, tokenService.refreshAccessToken(request));
    }

}
