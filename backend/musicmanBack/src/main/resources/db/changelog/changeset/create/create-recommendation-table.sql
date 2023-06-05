CREATE TABLE IF NOT EXISTS recommendation
(
    recommendation_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    song_id BIGINT REFERENCES song (song_id) ON DELETE CASCADE NOT NULL,
    till TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS recommendation_user_id_index ON recommendation (user_id);