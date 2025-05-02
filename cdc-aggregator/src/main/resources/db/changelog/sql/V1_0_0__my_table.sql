CREATE TABLE IF NOT EXISTS my_table(
    id              SERIAL          PRIMARY KEY,
    name            TEXT            NOT NULL,
    money           BIGINT          NOT NULL,
    description     TEXT            NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL        DEFAULT now(),
    updated_at      TIMESTAMPTZ     NOT NULL        DEFAULT now()
);
