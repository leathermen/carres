-- liquibase formatted sql
-- changeset ars:1674583274902-1
CREATE TABLE public."Car" (
  id BYTEA NOT NULL,
  available BOOLEAN,
  "createdAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  "createdBy" BYTEA,
  "manufacturedAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  "manufacturerName" VARCHAR(255),
  model VARCHAR(255),
  "updatedAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  CONSTRAINT "CarPK" PRIMARY KEY (id)
);
-- changeset ars:1674583274902-2
CREATE TABLE public."Reservation" (
  id BYTEA NOT NULL,
  cancelled BOOLEAN,
  "clientEmail" VARCHAR(255),
  "createdAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  "endsAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  "startsAt" TIMESTAMP(6) WITHOUT TIME ZONE,
  car_id BYTEA,
  CONSTRAINT "ReservationPK" PRIMARY KEY (id)
);
-- changeset ars:1674583274902-3
ALTER TABLE public."Reservation"
ADD CONSTRAINT "FK7iuqoc1fjk86qdh3r34gpykll" FOREIGN KEY (car_id) REFERENCES public."Car" (id);