package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.StatisticUserDTO;
import ru.vsu.csf.asashina.musicmanBack.service.StatisticService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static org.springframework.http.HttpStatus.OK;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.STATISTIC;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Выводит статистику по пользователю", tags = STATISTIC, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает статистику", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StatisticUserDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getStatisticByUserId(@PathVariable("userId") Long userId) {
        return ResponseBuilder.build(OK, statisticService.getUserStatistic(userId));
    }

    @GetMapping("/genre/{genreId}")
    @Operation(summary = "Выводит статистику по жанру", tags = STATISTIC, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает статистику", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StatisticUserDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Жанра не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getStatisticByGenreId(@PathVariable("genreId") Long genreId) {
        return ResponseBuilder.build(OK, statisticService.getGenreStatistic(genreId));
    }
}
