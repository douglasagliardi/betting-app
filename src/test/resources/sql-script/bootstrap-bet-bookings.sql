-- User #1
INSERT INTO betting.ACCOUNT (id, email)
VALUES (1, 'first.test@domain.com')
ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.WALLET (id, balance, user_id)
VALUES (1, 100, 1)
ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.BET_BOOKING (id, wallet_id, event_id, driver_id, amount, odd)
VALUES (1, 1, 1, 50, 50, 2)
ON CONFLICT (id) DO NOTHING;;

-- User #2
INSERT INTO betting.ACCOUNT (id, email)
VALUES (10, 'second.test@domain.com')
ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.WALLET (id, balance, user_id)
VALUES (10, 100, 10)
ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.BET_BOOKING (id, wallet_id, event_id, driver_id, amount, odd)
VALUES (10, 10, 50, 50, 50, 3)
ON CONFLICT (id) DO NOTHING;;
