package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.SongPageDTO;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.model.request.AddGenresToSongRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSongRequest;
import ru.vsu.csf.asashina.musicmanBack.service.SongService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PagingDTO.class))
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
                                         @RequestParam(value = "genres", required = false) List<Long> genreIds,
                                         @RequestParam(value = "singer", required = false) Long singerId) {
        return ResponseBuilder.build(
                songService.getAllSongs(pageNumber, size, title, sort, isAsc, genreIds, singerId), pageNumber, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Выводит информацию о песне", tags = SONG, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает информацию о песне", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Песни не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getSongById(@PathVariable("id") Long id) {
        return ResponseBuilder.build(OK, songService.getSongById(id));
    }

    @GetMapping(value = "/{id}/file", produces = "audio/mp3")
    @Operation(summary = "Возвращает mp3 файл", tags = SONG, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает файл", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Песни не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getSongFileById(Long id) throws IOException {
        FileSystemResource in = new FileSystemResource(songService.getFileFromSystem(id));
        var headers = new HttpHeaders();
        headers.setContentLength(in.contentLength());
        return new ResponseEntity(in, headers, OK);
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

    @PostMapping("/{id}/add-genres")
    @Operation(summary = "Добавление жанров к песни", tags = SONG, responses = {
            @ApiResponse(responseCode = "200", description = "Жанры добавлены", content = {
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
    public ResponseEntity<?> addGenresToSong(@PathVariable("id") Long id,
                                             @RequestBody @Valid AddGenresToSongRequest request) {
        songService.addGenresToSong(id, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление песни", tags = SONG, responses = {
            @ApiResponse(responseCode = "204", description = "Песня удалена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Отсутствует доступ", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Песня не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteSong(@PathVariable("id") Long id) {
        songService.deleteSongById(id);
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }
}
