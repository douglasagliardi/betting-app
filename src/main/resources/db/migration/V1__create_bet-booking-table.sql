CREATE SEQUENCE IF NOT EXISTS "wallet_id_seq" START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS "bet_booking_id_seq" START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS WALLET
(
    id      BIGINT
        CONSTRAINT "WALLET_ID_PK" PRIMARY KEY DEFAULT NEXTVAL('wallet_id_seq'),
    balance BIGINT NOT NULL                   DEFAULT 0 CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS BET_BOOKING
(
    id         BIGINT
        CONSTRAINT "BET_BOOKING_ID_PK" PRIMARY KEY DEFAULT NEXTVAL('bet_booking_id_seq'),
    user_id    BIGINT    NOT NULL
        CONSTRAINT "WALLET_ID_FK" REFERENCES WALLET (id),
    event_id   BIGINT    NOT NULL,
    amount     BIGINT    NOT NULL CHECK (amount > 0),
    currency   TEXT      NOT NULL,
    odd        NUMERIC(2, 6),
    created_at TIMESTAMP NOT NULL                  DEFAULT NOW(),
    completed  BOOLEAN                             DEFAULT FALSE
);

ALTER SEQUENCE "wallet_id_seq" OWNED BY WALLET.id;
ALTER SEQUENCE "bet_booking_id_seq" OWNED BY BET_BOOKING.id;