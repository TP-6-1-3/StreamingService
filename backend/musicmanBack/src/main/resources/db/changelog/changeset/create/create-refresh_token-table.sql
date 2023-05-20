CREATE TABLE IF NOT EXISTS refresh_token
(
    token UUID PRIMARY KEY,
    valid_till   TIMESTAMP NOT NULL,
    user_id       BIGINT    REFERENCES user_info (user_id) ON DELETE SET NULL
);