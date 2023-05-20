package ru.vsu.csf.asashina.musicmanBack.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import ru.vsu.csf.asashina.musicmanBack.exception.*;
import ru.vsu.csf.asashina.musicmanBack.model.dto.ExceptionDTO;
import ru.vsu.csf.asashina.musicmanBack.utils.ResponseBuilder;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseBuilder.build(INTERNAL_SERVER_ERROR, new ExceptionDTO("Internal server error"));
    }

    @ExceptionHandler({PasswordsDoNotMatch.class, WrongCredentialsException.class, PageException.class,
            SongFileException.class})
    public ResponseEntity<?> badRequestExceptionHandler(Exception e) {
        return ResponseBuilder.build(BAD_REQUEST, e);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(BindException e) {
        return ResponseBuilder.build(BAD_REQUEST,
                e.getBindingResult().getAllErrors().stream()
                        .map(error -> (FieldError) error)
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                (message1, message2) -> message1 + ", " + message2
                        )));
    }

    @ExceptionHandler({TokenExpiredException.class, TokenValidationException.class})
    public ResponseEntity<?> unauthorizedExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseBuilder.build(UNAUTHORIZED, e);
    }

    @ExceptionHandler({AccessDeniedException.class, UserNotVerifiedException.class})
    public ResponseEntity<?> forbiddenExceptionHandler(Exception e) {
        return ResponseBuilder.build(FORBIDDEN, e);
    }

    @ExceptionHandler({EntityDoesNotExistException.class})
    public ResponseEntity<?> notFoundExceptionHandler(Exception e) {
        return ResponseBuilder.build(NOT_FOUND, e);
    }

    @ExceptionHandler({VerificationExpiredException.class, AlreadyVerifiedUserException.class,
            NoSongInCollectionException.class})
    public ResponseEntity<?> methodNotAllowedExceptionHandler(Exception e) {
        return ResponseBuilder.build(METHOD_NOT_ALLOWED, e);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<?> conflictExceptionHandler(Exception e) {
        return ResponseBuilder.build(CONFLICT, e);
    }
}
