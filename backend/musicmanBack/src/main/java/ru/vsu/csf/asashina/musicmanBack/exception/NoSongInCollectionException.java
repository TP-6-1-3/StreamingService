package ru.vsu.csf.asashina.musicmanBack.exception;

public class NoSongInCollectionException extends RuntimeException {

    public NoSongInCollectionException() {
    }

    public NoSongInCollectionException(String message) {
        super(message);
    }
}
