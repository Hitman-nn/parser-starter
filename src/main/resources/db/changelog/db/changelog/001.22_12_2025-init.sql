--liquibase formatted sql

--changeset dlbogatyrev:1
CREATE TABLE IF NOT EXISTS bk_subscription
(
    id                BIGSERIAL PRIMARY KEY,
    bookmaker         VARCHAR(64)  NOT NULL,
    client_id         VARCHAR(128) NOT NULL,
    type              VARCHAR(64)  NOT NULL,         -- NEW_SPORTS / NEW_TOURNAMENTS_BY_SPORT / NEW_MATCHES_BY_TOURNAMENT
    target_id         VARCHAR(128) NOT NULL,         -- * / sportId / tournamentId
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_heartbeat_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- уникальность подписки
CREATE UNIQUE INDEX IF NOT EXISTS uk_bks
    ON bk_subscription (bookmaker, client_id, type, target_id);

-- ускоряем выборки
CREATE INDEX IF NOT EXISTS idx_bks_bookmaker_client
    ON bk_subscription (bookmaker, client_id);

CREATE INDEX IF NOT EXISTS idx_bks_type_target
    ON bk_subscription (type, target_id);

CREATE INDEX IF NOT EXISTS idx_bks_heartbeat
    ON bk_subscription (bookmaker, last_heartbeat_at);

--rollback DROP TABLE IF EXISTS bk_subscription;


--changeset dlbogatyrev:2
CREATE TABLE IF NOT EXISTS bk_catalog
(
    id         BIGSERIAL PRIMARY KEY,
    bookmaker  VARCHAR(64)  NOT NULL,
    kind       VARCHAR(32)  NOT NULL,                -- SPORT / TOURNAMENT
    entity_id  VARCHAR(128) NOT NULL,
    payload    BYTEA        NOT NULL,                -- protobuf bytes (Sport/Tournament)
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_bk_catalog
    ON bk_catalog (bookmaker, kind, entity_id);

CREATE INDEX IF NOT EXISTS idx_bk_catalog_bookmaker_kind
    ON bk_catalog (bookmaker, kind);

--rollback DROP TABLE IF EXISTS bk_catalog;


--changeset dlbogatyrev:3
CREATE TABLE IF NOT EXISTS bk_seen_catalog
(
    id            BIGSERIAL PRIMARY KEY,
    bookmaker     VARCHAR(64)  NOT NULL,
    kind          VARCHAR(32)  NOT NULL,             -- SPORT / TOURNAMENT / MATCH
    entity_id     VARCHAR(128) NOT NULL,
    first_seen_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_seen_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_bk_seen_catalog
    ON bk_seen_catalog (bookmaker, kind, entity_id);

CREATE INDEX IF NOT EXISTS idx_bk_seen_catalog_bookmaker_kind
    ON bk_seen_catalog (bookmaker, kind);

--rollback DROP TABLE IF EXISTS bk_seen_catalog;
