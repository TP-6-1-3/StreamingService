package ru.vsu.csf.asashina.musicmanBack.exception;

public class NoSongInLibraryException extends RuntimeException {

    public NoSongInLibraryException() {
    }

    public NoSongInLibraryException(String message) {
        super(message);
    }
}
