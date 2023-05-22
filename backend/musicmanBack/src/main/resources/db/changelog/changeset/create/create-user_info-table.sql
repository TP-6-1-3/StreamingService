CREATE TABLE IF NOT EXISTS user_info
(
    user_id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    first_name          VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    password_hash VARCHAR(60)  NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    nickname VARCHAR(100) NOT NULL UNIQUE,
    is_verified BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS email_user_info_index ON user_info (email);

CREATE UNIQUE INDEX IF NOT EXISTS nickname_user_info_index ON user_info (nickname);