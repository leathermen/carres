create table cars (
  id char(36) not null,
  manufacturer varchar(32600),
  model varchar(32600),
  manufactured_at timestamp(6),
  created_at timestamp(6),
  updated_at timestamp(6),
  created_by char(36),
  is_available boolean,
  primary key (id)
);
create table reservations (
  id char(36) not null,
  starts_at timestamp(6),
  ends_at timestamp(6),
  client_email varchar(32600),
  created_at timestamp(6),
  is_cancelled boolean,
  car_id char(36) not null,
  primary key (id)
);
alter table if exists reservations
add constraint reservation_car foreign key (car_id) references cars;