--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.1 (Debian 15.1-1.pgdg110+1)
SET statement_timeout = 0;
SET lock_timeout = 0;
-- SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
-- SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
-- SET check_function_bodies = false;
-- SET xmloption = content;
SET client_min_messages = warning;
-- SET row_security = off;
--
-- Data for Name: cars; Type: TABLE DATA; Schema: public; Owner: carres
--

INSERT INTO public.cars (
    id,
    available,
    created_at,
    created_by,
    manufactured_at,
    manufacturer,
    model,
    updated_at
  )
VALUES (
    'dce33c8c-e3de-4959-a68a-1608f0d55698',
    true,
    '2023-01-01 00:00:01',
    '34db4e9a-70a8-4127-8292-990acc7f5f24',
    '2004-06-19 01:05:06',
    'Saab',
    'Integra',
    '2023-01-01 00:00:01'
  );
INSERT INTO public.cars (
    id,
    available,
    created_at,
    created_by,
    manufactured_at,
    manufacturer,
    model,
    updated_at
  )
VALUES (
    '95fdef3d-e945-4a1b-a776-fd40edf9342e',
    true,
    '2023-01-01 00:00:01',
    '34db4e9a-70a8-4127-8292-990acc7f5f24',
    '2003-02-26 19:31:34',
    'Ford',
    'Escape',
    '2023-01-01 00:00:01'
  );
INSERT INTO public.cars (
    id,
    available,
    created_at,
    created_by,
    manufactured_at,
    manufacturer,
    model,
    updated_at
  )
VALUES (
    '66cd482f-3c6f-4fe0-b998-35cdf4035421',
    true,
    '2023-01-01 00:00:01',
    '34db4e9a-70a8-4127-8292-990acc7f5f24',
    '2003-12-06 20:56:11',
    'Jeep',
    'Wrangler',
    '2023-01-01 00:00:01'
  );
--
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: carres
--

INSERT INTO public.reservations (
    id,
    cancelled,
    client_email,
    created_at,
    ends_at,
    starts_at,
    car_id
  )
VALUES (
    '5c9ded5d-fd0b-41ac-8273-05268bf685dd',
    false,
    'dumbledore@hogwarts.com',
    '2023-02-03 10:00:00',
    '2023-02-06 12:45:00',
    '2023-02-06 12:00:00',
    'dce33c8c-e3de-4959-a68a-1608f0d55698'
  );
INSERT INTO public.reservations (
    id,
    cancelled,
    client_email,
    created_at,
    ends_at,
    starts_at,
    car_id
  )
VALUES (
    'f451e19c-fe1a-4196-ba59-fb147b9a3e24',
    true,
    'hagrid@hogwarts.com',
    '2023-02-06 20:26:14',
    '2023-02-08 17:15:00',
    '2023-02-08 16:15:00',
    'dce33c8c-e3de-4959-a68a-1608f0d55698'
  );
INSERT INTO public.reservations (
    id,
    cancelled,
    client_email,
    created_at,
    ends_at,
    starts_at,
    car_id
  )
VALUES (
    'c2e44fe1-8415-4aed-be05-f047e4022f33',
    false,
    'mcgonagall@hogwarts.com',
    '2023-01-25 14:17:17',
    '2023-02-11 20:45:00',
    '2023-02-11 20:00:00',
    '95fdef3d-e945-4a1b-a776-fd40edf9342e'
  );
--
-- PostgreSQL database dump complete
--