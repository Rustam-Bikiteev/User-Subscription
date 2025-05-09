CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    address VARCHAR(255),
    birth_date DATE
);

CREATE TABLE subscriptions (
    id UUID PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (id, name, email, age, address, birth_date) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Alice', 'alice@example.com', 30, '123 Main St', '1995-05-01'),
    ('22222222-2222-2222-2222-222222222222', 'Bob', 'bob@example.com', 25, '456 Elm St', '2000-10-15'),
    ('33333333-3333-3333-3333-333333333333', 'Charlie', 'charlie@example.com', 28, '789 Oak St', '1997-03-21');