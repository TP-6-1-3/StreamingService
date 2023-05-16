package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.GenreDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.service.GenreService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.OK;
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
}
