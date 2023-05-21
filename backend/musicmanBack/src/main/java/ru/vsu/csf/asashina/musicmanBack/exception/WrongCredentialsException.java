package ru.vsu.csf.asashina.musicmanBack.exception;

public class WrongCredentialsException extends RuntimeException {

    public WrongCredentialsException(String message) {
        super(message);
    }
}
