--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-07-16 04:37:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 841 (class 1247 OID 32798)
-- Name: group_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.group_type AS ENUM (
    'employee',
    'manager',
    'salesman'
);


ALTER TYPE public.group_type OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 32806)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    date_of_employ date NOT NULL,
    salary integer NOT NULL,
    employee_group public.group_type NOT NULL,
    boss_id bigint
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32805)
-- Name: employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employee_id_seq OWNER TO postgres;

--
-- TOC entry 4791 (class 0 OID 0)
-- Dependencies: 215
-- Name: employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employee_id_seq OWNED BY public.employee.id;


--
-- TOC entry 4637 (class 2604 OID 32809)
-- Name: employee id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee ALTER COLUMN id SET DEFAULT nextval('public.employee_id_seq'::regclass);


--
-- TOC entry 4785 (class 0 OID 32806)
-- Dependencies: 216
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employee VALUES (1, 'Bob Johnson', '2019-09-25', 60000, 'salesman', NULL);
INSERT INTO public.employee VALUES (3, 'John Doe', '2021-01-15', 40000, 'employee', 2);
INSERT INTO public.employee VALUES (5, 'Michael Clark', '2019-03-20', 80000, 'manager', 2);
INSERT INTO public.employee VALUES (6, 'Sarah Wilson', '2022-02-28', 45000, 'employee', 4);
INSERT INTO public.employee VALUES (7, 'Tom Jackson', '2017-08-12', 62000, 'salesman', 4);
INSERT INTO public.employee VALUES (8, 'Olivia Miller', '2018-09-02', 72000, 'manager', 5);
INSERT INTO public.employee VALUES (9, 'William White', '2020-04-05', 48000, 'employee', 8);
INSERT INTO public.employee VALUES (10, 'Sophia Garcia', '2019-10-15', 58000, 'salesman', 2);
INSERT INTO public.employee VALUES (11, 'James Young', '2018-06-28', 69000, 'manager', NULL);
INSERT INTO public.employee VALUES (12, 'Emma Lee', '2021-03-10', 42000, 'employee', 11);
INSERT INTO public.employee VALUES (13, 'Daniel Rodriguez', '2017-12-18', 63000, 'salesman', 11);
INSERT INTO public.employee VALUES (14, 'Ava Martinez', '2018-10-22', 73000, 'manager', 13);
INSERT INTO public.employee VALUES (15, 'Mia Hernandez', '2020-07-08', 49000, 'employee', 14);
INSERT INTO public.employee VALUES (16, 'Kevin Walter', '2020-05-13', 60000, 'manager', 5);
INSERT INTO public.employee VALUES (4, 'Eva Brown', '2020-11-30', 55000, 'salesman', 1);
INSERT INTO public.employee VALUES (2, 'Alice Smith', '2018-05-09', 70000, 'manager', NULL);
INSERT INTO public.employee VALUES (18, 'Mark Cukenberg', '2016-10-02', 60000, 'salesman', 7);
INSERT INTO public.employee VALUES (19, 'Leon Hemthvort', '2018-05-27', 75000, 'manager', 7);
INSERT INTO public.employee VALUES (20, 'Jo Kelher', '2022-04-11', 42000, 'employee', 7);
INSERT INTO public.employee VALUES (21, 'Anna Markova', '2023-01-05', 61000, 'manager', 18);
INSERT INTO public.employee VALUES (22, 'Daniel Bronson', '2021-03-18', 40000, 'employee', 18);


--
-- TOC entry 4792 (class 0 OID 0)
-- Dependencies: 215
-- Name: employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employee_id_seq', 22, true);


--
-- TOC entry 4639 (class 2606 OID 32811)
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);


--
-- TOC entry 4640 (class 2606 OID 32812)
-- Name: employee employee_boss_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_boss_id_fkey FOREIGN KEY (boss_id) REFERENCES public.employee(id);


-- Completed on 2024-07-16 04:37:35

--
-- PostgreSQL database dump complete
--

