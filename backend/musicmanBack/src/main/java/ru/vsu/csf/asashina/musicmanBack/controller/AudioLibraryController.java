package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UsersSongDTO;
import ru.vsu.csf.asashina.musicmanBack.service.AudioLibraryService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.OK;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.AUDIO_LIBRARY;

@RestController
@AllArgsConstructor
@RequestMapping("/library")
public class AudioLibraryController {

    private final AudioLibraryService audioLibraryService;

    @GetMapping("")
    @Operation(summary = "Выводит все композиции в аудиотеке", tags = AUDIO_LIBRARY, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все песни", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongPageDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getAllSongs(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                         @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                         @RequestParam(value = "title", required = false) String title,
                                         Authentication authentication) {
        return ResponseBuilder.build(audioLibraryService.getAllSongs(
                (String) authentication.getPrincipal(), pageNumber, size, title),
                pageNumber, size);
    }

    @GetMapping("/{songId}/exists")
    @Operation(summary = "Существует ли песня в аудиотеке", tags = AUDIO_LIBRARY, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращается результат", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsersSongDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Песня не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> songExists(@PathVariable("songId") Long songId, Authentication authentication) {
        return ResponseBuilder.build(OK, audioLibraryService.doesSongExists(
                (String) authentication.getPrincipal(), songId));
    }

    @PostMapping("/{songId}")
    @Operation(summary = "Добавляет песню в аудиотеку", tags = AUDIO_LIBRARY, responses = {
            @ApiResponse(responseCode = "200", description = "Песня добавлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Песня не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Песня уже в аудиотеке", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> addSong(@PathVariable("songId") Long songId, Authentication authentication) {
        audioLibraryService.addSong((String) authentication.getPrincipal(), songId);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }
}
