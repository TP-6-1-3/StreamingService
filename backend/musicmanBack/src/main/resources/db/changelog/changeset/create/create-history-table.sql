CREATE TABLE IF NOT EXISTS history
(
    history_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    song_id       BIGINT    REFERENCES song (song_id) ON DELETE CASCADE NOT NULL,
    play_timestamp TIMESTAMP NOT NULL
);