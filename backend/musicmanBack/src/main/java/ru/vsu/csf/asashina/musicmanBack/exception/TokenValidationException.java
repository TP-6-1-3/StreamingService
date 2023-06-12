package ru.vsu.csf.asashina.musicmanBack.exception;

public class TokenValidationException extends RuntimeException {

    public TokenValidationException(String message) {
        super(message);
    }
}
