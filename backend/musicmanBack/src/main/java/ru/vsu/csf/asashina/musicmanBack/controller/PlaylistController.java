package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.service.PlaylistService;
import ru.vsu.csf.asashina.musicmanBack.service.UserService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.PLAYLIST;

@RestController
@AllArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {

    //TODO: не забывать про первичные ключи и роль админа!

    private final PlaylistService playlistService;
    private final UserService userService;

    @GetMapping("")
    @Operation(summary = "Выводит все плейлисты пользователя", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все плейлисты", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlaylistPagesDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getAllPlaylists(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                             @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                             @RequestParam(value = "name", required = false) String name,
                                             Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        return ResponseBuilder.build(
                playlistService.getAll(user.getUserId(), pageNumber, size, name),
                pageNumber,
                size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Выводит плейлист пользователя с песнями", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает плейлист", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistWithSongsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Плейлист не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getPlaylistById(@PathVariable("id") String id, Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        boolean isAdmin = userService.isAdmin(user);
        return ResponseBuilder.build(OK, playlistService.getPlaylistById(id, isAdmin, user.getUserId()));
    }

    @PostMapping("")
    @Operation(summary = "Создает новый плейлист", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "201", description = "Плейлист создан", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Плейлист не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> createPlaylist(
            @RequestBody @Valid CreatePlaylistRequest request,
            Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        playlistService.createPlaylist(request, user);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }
}
