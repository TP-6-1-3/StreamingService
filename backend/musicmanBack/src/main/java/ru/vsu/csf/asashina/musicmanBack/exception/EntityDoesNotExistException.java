package ru.vsu.csf.asashina.musicmanBack.exception;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
