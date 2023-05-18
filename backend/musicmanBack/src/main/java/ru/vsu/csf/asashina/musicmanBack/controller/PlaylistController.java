package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PlaylistDTO;
import ru.vsu.csf.asashina.musicmanBack.service.PlaylistService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.PLAYLIST;

@RestController
@AllArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    //TODO: не забывать про первичные ключи и роль админа!

    private final PlaylistService playlistService;

    @GetMapping("")
    @Operation(summary = "Выводит все плейлисты пользователя", tags = PLAYLIST, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все плейлисты", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PlaylistDTO.class))
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
        return ResponseBuilder.build(playlistService.getAll(
                (String) authentication.getPrincipal(), pageNumber, size, name),
                pageNumber, size);
    }
}
