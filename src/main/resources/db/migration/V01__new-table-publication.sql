CREATE TABLE publication (
	id INTEGER SERIAL PRIMARY KEY,
	title TEXT NOT NULL,
	notice TEXT NOT NULL,
	author TEXT NOT NULL,
	create_date DATE DEFAULT NOW(),
	update_date DATE
);