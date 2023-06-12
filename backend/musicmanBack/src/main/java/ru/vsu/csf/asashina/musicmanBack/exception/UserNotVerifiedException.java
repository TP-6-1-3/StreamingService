package ru.vsu.csf.asashina.musicmanBack.exception;

public class UserNotVerifiedException extends RuntimeException {

    public UserNotVerifiedException(String message) {
        super(message);
    }
}
