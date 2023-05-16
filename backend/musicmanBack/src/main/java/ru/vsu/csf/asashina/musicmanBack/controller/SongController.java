package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSongRequest;
import ru.vsu.csf.asashina.musicmanBack.service.SongService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.SONG;

@RestController
@RequestMapping("/songs")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("")
    @Operation(summary = "Выводит все песни по страницам", tags = SONG, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все песни по страницам", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongPageDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getAllSongs(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                         @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                         @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "sort", required = false, defaultValue = "BY_TITLE") SongSort sort,
                                         @RequestParam(value = "isAsc", required = false, defaultValue = "true") Boolean isAsc,
                                         @RequestParam(value = "genres", required = false) Long[] genreId,
                                         @RequestParam(value = "singer", required = false) Long singerId) {
        return ResponseBuilder.build(
                songService.getAllSongs(pageNumber, size, title, sort, isAsc, genreId, singerId), pageNumber, size);
    }

    @PostMapping(value = "", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Создание новой песни", tags = SONG, responses = {
            @ApiResponse(responseCode = "201", description = "Песня создана", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Отсутствует доступ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Не существует исполнитель или жанр", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> createSong(@RequestBody @Valid CreateSongRequest request) throws UnsupportedAudioFileException, IOException {
        songService.createSong(request);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }
}
