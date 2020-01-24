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

CREATE TABLE IF NOT EXISTS topics
(
    id      bigserial PRIMARY KEY,
    name    text NOT NULL,
    user_id int  NOT NULL REFERENCES users (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS attachments
(
    id       bigserial PRIMARY KEY,
    owner_id int     NOT NULL,
    type     varchar NOT NULL,
    vk_id    int     NOT NULL,
    topic_id int     NULL REFERENCES topics (id) on delete cascade
);
