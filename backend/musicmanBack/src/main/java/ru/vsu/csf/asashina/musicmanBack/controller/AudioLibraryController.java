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
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.service.AudioLibraryService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

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
}
