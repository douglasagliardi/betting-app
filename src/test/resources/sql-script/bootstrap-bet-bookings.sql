-- User #1
INSERT INTO betting.ACCOUNT (id, email)
VALUES (1, 'first.test@domain.com');
INSERT INTO betting.WALLET (id, balance, user_id)
VALUES (1, 100, 1);
INSERT INTO betting.BET_BOOKING (id, wallet_id, event_id, amount, odd)
VALUES (1, 1, 50, 5000, 2);

-- User #2
INSERT INTO betting.ACCOUNT (id, email)
VALUES (10, 'second.test@domain.com');
INSERT INTO betting.WALLET (id, balance, user_id)
VALUES (10, 100, 10);
INSERT INTO betting.BET_BOOKING (id, wallet_id, event_id, amount, odd)
VALUES (10, 10, 50, 2500, 3);
