package ru.vsu.csf.asashina.musicmanBack.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UuidUtil {

    public static String generateRandomUUIDInString() {
        return UUID.randomUUID().toString();
    }
}
