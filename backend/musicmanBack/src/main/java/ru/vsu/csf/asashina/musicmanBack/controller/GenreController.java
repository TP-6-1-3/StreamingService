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
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateGenreRequest;
import ru.vsu.csf.asashina.musicmanBack.service.GenreService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.*;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.GENRE;

@RestController
@AllArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("")
    @Operation(summary = "Выводит все жанры", tags = GENRE, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все жанры", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GenreDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getAllGenres() {
        return ResponseBuilder.build(OK, genreService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Возвращает жанр по ИД", tags = GENRE, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает жанр", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GenreDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Жанр не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getGenreById(@PathVariable("id") Long id) {
        return ResponseBuilder.build(OK, genreService.getGenreById(id));
    }

    @PostMapping("")
    @Operation(summary = "Создает новый жанр", tags = GENRE, responses = {
            @ApiResponse(responseCode = "201", description = "Жанр создан", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Жанр уже существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> createGenre(@RequestBody @Valid CreateGenreRequest request) {
        genreService.createGenre(request);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаляет жанр", tags = GENRE, responses = {
            @ApiResponse(responseCode = "204", description = "Жанр удален", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Жанр не найден", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteGenreById(id);
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }
}
