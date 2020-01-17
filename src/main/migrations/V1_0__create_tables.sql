CREATE TABLE IF NOT EXISTS users
(
    id    serial PRIMARY KEY,
    vk_id integer UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS incomming_messages
(
    id      bigserial PRIMARY KEY,
    value   text NOT NULL,
    user_id int  NOT NULL REFERENCES users (id) on delete cascade
);

