CREATE TABLE IF NOT EXISTS verification
(
    verification_id VARCHAR(40) PRIMARY KEY,
    user_id      BIGINT REFERENCES user_info (user_id) ON DELETE CASCADE NOT NULL,
    valid_till TIMESTAMP NOT NULL,
    code VARCHAR(40) NOT NULL UNIQUE
);

CREATE UNIQUE INDEX IF NOT EXISTS code_verification_index ON verification (code);