CREATE TABLE IF NOT EXISTS client (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT,
    phone TEXT,
    cpf TEXT NOT NULL,
    birth_date DATE NOT NULL,
    monthly_income NUMERIC,
    registration_date DATE NOT NULL
);
