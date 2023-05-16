package ru.vsu.csf.asashina.musicmanBack.model.enumeration;

import lombok.Getter;

public enum SongSort {

    BY_TITLE("BY_TITLE", "title"),
    BY_DURATION("BY_DURATION", "duration");

    @Getter
    private final String sort;
    @Getter
    private final String queryParam;

    SongSort(String sort, String queryParam) {
        this.sort = sort;
        this.queryParam = queryParam;
    }
}
