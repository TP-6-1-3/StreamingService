package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.PlaylistPagesDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdatePlaylistRequest;
import ru.vsu.csf.asashina.musicmanBack.service.PlaylistService;
import ru.vsu.csf.asashina.musicmanBack.service.UserService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.PLAYLIST;

@RestController
@AllArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {

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
                                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
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
    public ResponseEntity<?> getPlaylistById(@PathVariable("id") UUID id, Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        boolean isAdmin = userService.isAdmin(user);
        return ResponseBuilder.build(OK, playlistService.getPlaylistById(id, isAdmin, user.getUserId()));
    }

    @GetMapping("/{id}/exists/{songId}")
    @Operation(summary = "Выводит информацию о существовании песни в плейлисте", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает результат", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongExistsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Плейлист или песня не существуют", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> isSongInPlaylist(@PathVariable("id") UUID id,
                                              @PathVariable("songId") Long songId,
                                              Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, playlistService.isSongInPlaylist(id, songId, user.getUserId()));
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
    public ResponseEntity<?> createPlaylist(@RequestBody @Valid CreatePlaylistRequest request,
                                            Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        playlistService.createPlaylist(request, user);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }

    @PostMapping("/{id}/add-song/{songId}")
    @Operation(summary = "Добавить песню в плейлист", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Песня добавлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Плейлист или песня не существуют", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Песня уже есть в плейлисте", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> addSongToPlaylist(@PathVariable("id") UUID id,
                                               @PathVariable("songId") Long songId,
                                               Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        playlistService.addSongToPlaylist(id, songId, user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление плейлиста", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Плейлист обновлен", content = {
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
    public ResponseEntity<?> updatePlaylist(@PathVariable("id") UUID id,
                                            @RequestBody @Valid UpdatePlaylistRequest request,
                                            Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        playlistService.updatePlaylist(id, request, user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление плейлиста", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "204", description = "Плейлист удален", content = {
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
    public ResponseEntity<?> deletePlaylist(@PathVariable("id") UUID id, Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        boolean isAdmin = userService.isAdmin(user);
        playlistService.deletePlaylist(id, user.getUserId(), isAdmin);
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

    @DeleteMapping("/{id}/delete-song/{songId}")
    @Operation(summary = "Удаление песни из плейлиста", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Песня удалена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Плейлист или песня не существуют", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "405", description = "Песни нет в плейлисте", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteSongFromPlaylist(@PathVariable("id") UUID id,
                                                    @PathVariable("songId") Long songId,
                                                    Authentication authentication) {
        UserDTO user = userService.getUserByEmailWithVerificationCheck((String) authentication.getPrincipal());
        playlistService.deleteSongFromPlaylist(id, songId, user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }
}
