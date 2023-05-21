CREATE TABLE IF NOT EXISTS user_song
(
    user_song_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    song_id       BIGINT    REFERENCES song (song_id) ON DELETE CASCADE NOT NULL
);