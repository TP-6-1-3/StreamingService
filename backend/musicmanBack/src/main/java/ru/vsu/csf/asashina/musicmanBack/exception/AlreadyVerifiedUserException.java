package ru.vsu.csf.asashina.musicmanBack.exception;

public class AlreadyVerifiedUserException extends RuntimeException {

    public AlreadyVerifiedUserException(String message) {
        super(message);
    }
}
