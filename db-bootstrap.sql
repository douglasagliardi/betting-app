drop table bet_booking;
drop table wallet;
drop table account;

INSERT INTO betting.ACCOUNT (id, email) VALUES (1, 'first.user@domain.com');
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (1, 100, 1);
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (2, 100, 1);

INSERT INTO betting.ACCOUNT (id, email) VALUES (2, 'second.user@domain.com');
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (10, 100, 2);
INSERT INTO betting.WALLET (id, balance, user_id) VALUES (20, 100, 2);