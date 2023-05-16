CREATE TABLE IF NOT EXISTS singer
(
    singer_id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name          VARCHAR(255) NOT NULL,
    description       TEXT NOT NULL
);