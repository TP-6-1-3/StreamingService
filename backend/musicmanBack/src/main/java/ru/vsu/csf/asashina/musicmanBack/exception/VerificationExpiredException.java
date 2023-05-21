package ru.vsu.csf.asashina.musicmanBack.exception;

public class VerificationExpiredException extends RuntimeException {

    public VerificationExpiredException(String message) {
        super(message);
    }
}
