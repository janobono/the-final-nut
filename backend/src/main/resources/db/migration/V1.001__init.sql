-- SEQUENCE
create sequence nut_user_seq start with 1 increment by 1 no cycle;

-- TABLE
create table nut_user
(
    id       bigint       not null,
    username varchar(255) not null,
    email    varchar(255) not null,
    enabled  bool         not null default false,
    locked   bool         not null default false
);

-- PK
alter table nut_user
    add primary key (id);

-- UNIQUE
alter table nut_user
    add constraint u_user_login unique (username);
alter table nut_user
    add constraint u_user_email unique (email);
