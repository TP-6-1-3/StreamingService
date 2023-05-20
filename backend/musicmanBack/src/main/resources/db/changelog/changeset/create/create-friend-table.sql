CREATE TABLE IF NOT EXISTS friend
(
    user_friend_id VARCHAR(40) PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    friend_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL
);

CREATE INDEX IF NOT EXISTS friend_user_id_index ON friend (user_id);