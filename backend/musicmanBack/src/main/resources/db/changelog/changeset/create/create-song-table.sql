CREATE TABLE IF NOT EXISTS song
(
    song_id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    duration       TIME NOT NULL,
    song_path VARCHAR(255)  NOT NULL,
    singer_id BIGINT REFERENCES singer(singer_id) ON DELETE CASCADE NOT NULL
);

CREATE INDEX IF NOT EXISTS title_song_index ON song (title);