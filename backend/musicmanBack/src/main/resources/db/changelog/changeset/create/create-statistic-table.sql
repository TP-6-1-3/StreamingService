CREATE TABLE IF NOT EXISTS statistic
(
    statistic_id VARCHAR(40) PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE SET NULL NOT NULL,
    genre_id       BIGINT    REFERENCES genre (genre_id) ON DELETE SET NULL NOT NULL,
    amount BIGINT DEFAULT 0
);