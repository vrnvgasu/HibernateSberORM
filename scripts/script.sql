create table "user" (
    id text primary key ,
    first_name text,
    last_name text,
    birth_date text
);

truncate "user" restart identity;
insert into "user" values ('1','Ivan','Ivanov','1900-01-10');