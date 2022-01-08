Java - 11 coreto
Maven - 3.6.3
War Package
Tomcat - 9.0.54
Postgresql - 14
Vytvorit v postgresql databazu:
nazov : semestralkajava 
meno : postgres
heslo : postgres
vytvorit tabulku v scheme public prikazom

create table mena
(
id          serial
constraint mena_pk
primary key,
currname    text,
icon        text,
description text,
active      boolean,
created_at  date,
updated_at  date
);

alter table mena
owner to postgres;

create unique index mena_id_uindex
on mena (id);

