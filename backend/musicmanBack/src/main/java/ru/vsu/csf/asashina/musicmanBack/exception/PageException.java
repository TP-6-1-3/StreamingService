package ru.vsu.csf.asashina.musicmanBack.exception;

public class PageException extends RuntimeException {

    public PageException() {
    }

    public PageException(String message) {
        super(message);
    }
}
