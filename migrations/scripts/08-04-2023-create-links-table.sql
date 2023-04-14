CREATE SEQUENCE IF NOT EXISTS link_id_seq;

CREATE TABLE IF NOT EXISTS links
(
    id BIGINT PRIMARY KEY DEFAULT nextval('link_id_seq'),
    domain TEXT NOT NULL,
    path TEXT NOT NULL UNIQUE,
    last_activity TIMESTAMP NOT NULL DEFAULT now()
);

ALTER SEQUENCE link_id_seq OWNED BY links.id;
