package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.FriendPagesDTO;
import ru.vsu.csf.asashina.musicmanBack.service.FriendService;
import ru.vsu.csf.asashina.musicmanBack.service.UserService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.OK;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.FRIEND;

@RestController
@AllArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "Выводит всех друзей пользователя", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает всех друзей", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FriendPagesDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getAllFriends(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                           @RequestParam(value = "nickname", required = false) String nickname,
                                           Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        return ResponseBuilder.build(
                friendService.getFriends(user.getUserId(), pageNumber, size, nickname),
                pageNumber,
                size);
    }

    @PostMapping("/{nickname}")
    @Operation(summary = "Добавить пользователя в друзья", tags = FRIEND, responses = {
            @ApiResponse(responseCode = "200", description = "Пользователь добавлен", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Пользователь уже в друзьях", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> addFriend(@PathVariable("nickname") String nickname, Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        friendService.addFriend(user, nickname);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }
}
