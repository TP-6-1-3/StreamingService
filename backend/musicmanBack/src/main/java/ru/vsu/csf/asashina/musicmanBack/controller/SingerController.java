package ru.vsu.csf.asashina.musicmanBack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.service.SingerService;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import static ru.vsu.csf.asashina.musicmanBack.model.constant.Tag.SONG;

@RestController
@AllArgsConstructor
@RequestMapping("/singers")
public class SingerController {

    private final SingerService singerService;

    @GetMapping("")
    @Operation(summary = "Выводит всех исполнителей по страницам", tags = SONG, responses = {
            @ApiResponse(responseCode = "200", description = "Возвращает все песни по страницам", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PagingDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> getAllSingers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "isAsc", required = false, defaultValue = "true") Boolean isAsc) {
        return ResponseBuilder.build(singerService.getAllSingers(pageNumber, size, name, isAsc), pageNumber, size);
    }
}
