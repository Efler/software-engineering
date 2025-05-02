CREATE TABLE IF NOT EXISTS change_log (
    id              SERIAL          PRIMARY KEY,
    table_name      TEXT            NOT NULL,
    record_id       INT             NOT NULL,
    changed_at      TIMESTAMPTZ     NOT NULL        DEFAULT now(),
    new_data        JSONB,
    old_data        JSONB,
    operation       TEXT            NOT NULL        DEFAULT 'UPDATE'
);
