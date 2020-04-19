create table "UserAccount"
(
    "userId" serial not null,
    login varchar(32) not null,
    password varchar(32) not null,
    token varchar(128)
);

create unique index useraccount_login_uindex
    on "UserAccount" (login);

create unique index useraccount_password_uindex
    on "UserAccount" (password);

create unique index useraccount_token_uindex
    on "UserAccount" (token);

create unique index useraccount_userid_uindex
    on "UserAccount" ("userId");

alter table "UserAccount"
    add constraint useraccount_pk
        primary key ("userId");