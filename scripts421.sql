alter table student
    add constraint chk_student_age check (age > 16),
    add constraint uniq_student_name unique (name),
    alter column age default 20;

alter table faculty
    add constraint uniq_faculty_color unique (color),
    add constraint uniq_faculty_name unique (name);