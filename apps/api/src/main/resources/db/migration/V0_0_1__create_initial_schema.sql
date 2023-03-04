create table cars (
  id uuid not null,
  available boolean,
  created_at timestamp(6),
  created_by uuid,
  manufactured_at timestamp(6),
  manufacturer varchar(255),
  model varchar(255),
  updated_at timestamp(6),
  primary key (id)
);
create table reservations (
  id uuid not null,
  cancelled boolean,
  client_email varchar(255),
  created_at timestamp(6),
  ends_at timestamp(6),
  starts_at timestamp(6),
  car_id uuid,
  primary key (id)
);
alter table if exists reservations
add constraint FKkoxuu4vp8ex6mcc642sa11iyc foreign key (car_id) references cars;