CREATE DATABASE canteen;

USE canteen;

CREATE TABLE users (
    username VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO users VALUES ('admin', '1234');

CREATE TABLE inventory (
    item_name VARCHAR(50),
    price DOUBLE,
    stock INT
);

INSERT INTO inventory VALUES
('Idli', 30, 50),
('Coffee', 20, 100),
('Sandwich', 50, 40),
('Dosa', 60, 30),
('Tea', 15, 80);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50),
    total DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);