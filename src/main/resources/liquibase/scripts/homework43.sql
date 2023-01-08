-- liquibase formatted sql

--changeset ilyaber:index_find_student_by_name
create index if not exists idx_student_name on student (name);

--changeset ilyaber:index_find_faculty_by_name_and_color
create index if not exists idx_faculty_name_color on faculty (name, color);