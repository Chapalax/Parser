CREATE SEQUENCE IF NOT EXISTS link_id_seq;

CREATE TABLE IF NOT EXISTS links
(
    id BIGINT PRIMARY KEY DEFAULT nextval('link_id_seq'),
    path TEXT NOT NULL UNIQUE,
    last_activity TIMESTAMP NOT NULL DEFAULT now(),
    action_count INT NOT NULL DEFAULT 0,
    checked_at TIMESTAMP NOT NULL DEFAULT now()
);

ALTER SEQUENCE link_id_seq OWNED BY links.id;
