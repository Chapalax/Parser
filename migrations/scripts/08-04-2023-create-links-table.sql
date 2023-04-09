CREATE SEQUENCE IF NOT EXISTS link_id_seq;

CREATE TABLE IF NOT EXISTS links
(
    id BIGINT PRIMARY KEY DEFAULT nextval('link_id_seq'),
    domain TEXT NOT NULL,
    path TEXT NOT NULL,
    last_activity DATE NOT NULL DEFAULT now()
);
