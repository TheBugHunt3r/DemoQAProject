CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

DELETE FROM users;

INSERT INTO users (username, password, role)
VALUES ('adminUi', 'enc_UGFzc3dvcmQxMjMh', 'adminUi');

INSERT INTO users (username, password, role)
VALUES ('admin6', 'enc_UGFzc3dvcmQxMjMh', 'admin6');