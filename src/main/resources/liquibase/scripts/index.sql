--liquibase formatted sql
--changeset mhasiyatova:1

create index students_name_idx on student (name);

--changeset mhasiyatova:2

create index faculty_name_color_idx on faculty (name, color);