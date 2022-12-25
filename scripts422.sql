create table if not exists person
(
    id integer not null,
    name varchar not null,
    age integer not null,
    driver_license boolean not null,
    car_id integer,
    constraint person_id_pk primary key (id),
    constraint chk_person_car_id_fk foreign key (car_id)
        references car (id) match simple
        on update no action
        on delete set null,
);

create table if not exists car
(
    id integer not null,
    brand varchar not null,
    model varchar not null,
    price integer not null,
    constraint car_id_pk primary key (id)
);