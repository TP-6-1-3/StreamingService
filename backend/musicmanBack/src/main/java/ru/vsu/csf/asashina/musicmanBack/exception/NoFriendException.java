package ru.vsu.csf.asashina.musicmanBack.exception;

public class NoFriendException extends RuntimeException {

    public NoFriendException() {
    }

    public NoFriendException(String message) {
        super(message);
    }
}
