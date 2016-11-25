CREATE TABLE terrorist_settings
(
  id serial NOT NULL,
  days character varying(255),
  hour integer,
  CONSTRAINT terrorist_settings_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE terrorists
(
  id serial NOT NULL,
  birthplace character varying(255),
  birthday timestamp without time zone,
  databeg timestamp without time zone,
  dataend timestamp without time zone,
  isactive boolean,
  midname character varying(255),
  name character varying(255),
  surname character varying(255),
  CONSTRAINT terrorists_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE updates
(
  id serial NOT NULL,
  date timestamp without time zone,
  message character varying(255),
  status boolean,
  type character varying(255),
  CONSTRAINT updates_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE users
(
  id serial NOT NULL,
  login character varying(255),
  password character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

INSERT INTO users (login, password) VALUES ('admin', 'admin');