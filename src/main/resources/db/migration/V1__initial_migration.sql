create table if not exists refresh_tokens
(
    id      bigserial primary key,
    token   varchar(255) unique not null,
    ip      varchar(255)        not null,
    user_id bigint              not null
);

create table if not exists users
(
    id               bigserial primary key,
    guid             uuid unique   not null,
    refresh_token_id bigint unique references refresh_tokens (id) on delete set null
);