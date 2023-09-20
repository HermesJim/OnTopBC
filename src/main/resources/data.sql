CREATE TABLE IF NOT EXISTS account(
	id number NOT NULL PRIMARY KEY
);

insert into account (id) values (0245253419);
insert into account (id) values (1885226711);

CREATE TABLE IF NOT EXISTS transaction(
	id number NOT NULL PRIMARY KEY,
	timestamp timestamp,
	owner_account_id number,
	source_account_id number,
	target_account_id number,
	amount number,
	status varchar(255)
);

CREATE SEQUENCE IF NOT EXISTS transaction_seq
  START WITH 1
  INCREMENT BY 1;

insert into transaction (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (NEXTVAL('transaction_seq'), '2023-08-08 08:00:00.0', 0245253419, 1885226711, 0245253419, 8000);

insert into transaction (id, timestamp, owner_account_id, source_account_id, target_account_id, amount)
values (NEXTVAL('transaction_seq'), '2023-08-08 08:00:00.0', 1885226711, 1885226711, 0245253419, 8000);
