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
import ru.vsu.csf.asashina.musicmanBack.model.dto.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.SingerPagesDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.page.SongPagesDTO;
import ru.vsu.csf.asashina.musicmanBack.model.enumeration.SongSort;
import ru.vsu.csf.asashina.musicmanBack.model.request.CreateSingerRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdateSingerRequest;
import ru.vsu.csf.asashina.musicmanBack.service.SingerService;
import ru.vsu.csf.asashina.musicmanBack.service.SongService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.SINGER;

@RestController
@AllArgsConstructor
@RequestMapping("/singers")
public class SingerController {

    private final SingerService singerService;
    private final SongService songService;

    @GetMapping("")
    @Operation(summary = "Выводит всех исполнителей по страницам", tags = SINGER, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает всех исполнителей по страницам", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SingerPagesDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getAllSingers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                           @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                           @RequestParam(value = "isAsc", required = false, defaultValue = "true") Boolean isAsc) {
        return ResponseBuilder.build(singerService.getAllSingers(pageNumber, size, name, isAsc), pageNumber, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Выводит исполнителя по ИД", tags = SINGER, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает информацию об исполнителе", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SingerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Исполнитель не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getSingerById(@PathVariable("id") Long id) {
        return ResponseBuilder.build(OK, singerService.getSingerById(id));
    }

    @GetMapping("/{id}/songs")
    @Operation(summary = "Выводит все песни исполнителя по страницам", tags = SINGER, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все песни по страницам", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SongPagesDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Исполнитель не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getSingersSongs(@PathVariable("id") Long id,
                                             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                             @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        singerService.getSingerById(id);
        return ResponseBuilder.build(
                songService.getAllSongs(
                        pageNumber, size, "", SongSort.BY_TITLE, true, new ArrayList<>(), id),
                pageNumber, size);
    }

    @PostMapping("")
    @Operation(summary = "Создание исполнителя", tags = SINGER, responses = {
            @ApiResponse(responseCode = "201", description = "Исполнитель был создан", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> createSinger(@RequestBody @Valid CreateSingerRequest request) {
        singerService.createSinger(request);
        return ResponseBuilder.buildWithoutBodyResponse(CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление описания исполнителя", tags = SINGER, responses = {
            @ApiResponse(responseCode = "200", description = "Описание обновлено", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Исполнитель не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> updateSinger(@PathVariable("id") Long id,
                                          @RequestBody @Valid UpdateSingerRequest request) {
        singerService.updateSingerById(id, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление исполнителя", tags = SINGER, responses = {
            @ApiResponse(responseCode = "204", description = "Исполнитель удален", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Исполнитель не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteSinger(@PathVariable("id") Long id) {
        singerService.deleteSingerById(id);
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }
}
