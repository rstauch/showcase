CREATE TABLE IF NOT EXISTS todos
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR               NOT NULL,
    content    TEXT                  NOT NULL,
    completed  BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMPTZ           NOT NULL,
    updated_at TIMESTAMPTZ           NOT NULL
);