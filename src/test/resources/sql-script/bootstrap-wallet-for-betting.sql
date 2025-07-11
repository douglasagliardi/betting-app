INSERT INTO betting.ACCOUNT (id, email) VALUES (1, 'test@domain.com') ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (10, 100, 1) ON CONFLICT (id) DO NOTHING;;
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (20, 100, 1) ON CONFLICT (id) DO NOTHING;;