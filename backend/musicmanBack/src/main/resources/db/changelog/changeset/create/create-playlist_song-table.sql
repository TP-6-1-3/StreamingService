CREATE TABLE IF NOT EXISTS playlist_song
(
    playlist_song_id VARCHAR(40) PRIMARY KEY,
    playlist_id      VARCHAR(40) REFERENCES playlist (playlist_id) ON DELETE CASCADE NOT NULL,
    song_id BIGINT REFERENCES song (song_id) ON DELETE CASCADE NOT NULL
);