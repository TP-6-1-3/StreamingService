package ru.vsu.csf.asashina.musicmanBack.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PagingDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.PagingInfoDTO;

import java.util.List;
import java.util.Map;

@UtilityClass
public class ResponseBuilder {

    public static ResponseEntity<?> build(HttpStatus httpStatus, Map<String, Object> data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> build(HttpStatus httpStatus, Object data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> build(Page<?> data, Integer pageNumber, Integer size) {
        return build(new PagingInfoDTO(pageNumber, size, data.getTotalPages()),
                data.getContent());
    }

    public static <T> ResponseEntity<?> build(PagingInfoDTO pagingInfoDTO, List<T> data) {
        return ResponseEntity.status(HttpStatus.OK).body(new PagingDTO<T>(pagingInfoDTO, data));
    }

    public static ResponseEntity<?> build(HttpStatus httpStatus, Exception e) {
        return build(httpStatus, new ExceptionDTO(e.getMessage()));
    }

    public static ResponseEntity<?> buildWithoutBodyResponse(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }
}
