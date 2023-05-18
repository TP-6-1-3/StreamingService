CREATE TABLE IF NOT EXISTS playlist
(
    playlist_id VARCHAR(40) PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL
);