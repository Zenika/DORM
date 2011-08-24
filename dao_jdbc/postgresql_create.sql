-- Database: "DORM_DATA"

-- DROP DATABASE "DORM_DATA";

CREATE DATABASE "DORM_DATA"
  WITH ENCODING = 'UTF8';

-- Table: dorm_metadata

-- DROP TABLE dorm_metadata;

CREATE TABLE dorm_metadata
(
  id serial NOT NULL,
  dorm_qualifier character varying(255) NOT NULL,
  dorm_version character varying(255) NOT NULL,
  dorm_type character varying(255) NOT NULL,
  CONSTRAINT id_metadata PRIMARY KEY (id),
  CONSTRAINT unique_qualifier UNIQUE (dorm_qualifier)
)
WITH (
  OIDS=FALSE
);

-- Table: dorm_dependencies

-- DROP TABLE dorm_dependencies;

CREATE TABLE dorm_dependencies
(
  id_parent serial NOT NULL,
  id_child serial NOT NULL,
  id serial NOT NULL,
  dorm_usage character varying(255) NOT NULL,
  CONSTRAINT id_dependencies PRIMARY KEY (id),
  CONSTRAINT id_child_fk FOREIGN KEY (id_child)
      REFERENCES dorm_metadata (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT id_parent_fk FOREIGN KEY (id_parent)
      REFERENCES dorm_metadata (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unique_dependency UNIQUE (id_parent, id_child)
)
WITH (
  OIDS=FALSE
);

-- Table: dorm_extension

-- DROP TABLE dorm_extension;

CREATE TABLE dorm_extension
(
  id serial NOT NULL,
  dorm_key character varying(255) NOT NULL,
  dorm_value character varying(255) NOT NULL,
  dorm_metadata_fk serial NOT NULL,
  CONSTRAINT id_extension PRIMARY KEY (id),
  CONSTRAINT fk_extension_to_metadata FOREIGN KEY (dorm_metadata_fk)
      REFERENCES dorm_metadata (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

