CREATE TABLE IF NOT EXISTS playlist_song
(
    playlist_song_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    playlist_id      UUID REFERENCES playlist (playlist_id) ON DELETE CASCADE NOT NULL,
    song_id BIGINT REFERENCES song (song_id) ON DELETE CASCADE NOT NULL
);