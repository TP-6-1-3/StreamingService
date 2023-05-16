package ru.vsu.csf.asashina.musicmanBack.exception;

public class PasswordsDoNotMatch extends RuntimeException {

    public PasswordsDoNotMatch() {
    }

    public PasswordsDoNotMatch(String message) {
        super(message);
    }
}
