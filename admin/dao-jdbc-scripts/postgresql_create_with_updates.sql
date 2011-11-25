-- CREATE DATABASE

DROP TABLE IF EXISTS dorm_versionning;

CREATE TABLE dorm_versionning
(
  id serial NOT NULL,
  version character varying(255) NOT NULL,
  CONSTRAINT dorm_versionning_id PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dorm_metadata;

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

DROP TABLE IF EXISTS dorm_dependencies;

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

DROP TABLE IF EXISTS dorm_extension;

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


-- UPDATE 1

DELETE FROM dorm_extension;
ALTER TABLE dorm_extension ADD COLUMN dorm_extension_qualifier character varying(255) NOT NULL;
ALTER TABLE dorm_extension ADD COLUMN dorm_name character varying(255) NOT NULL;
insert into dorm_versionning (version) values (1);


-- UPDATE 2

ALTER TABLE dorm_dependencies RENAME dorm_usage  TO usage;
ALTER TABLE dorm_extension RENAME dorm_key  TO property_key;
ALTER TABLE dorm_extension RENAME dorm_value  TO property_value;
ALTER TABLE dorm_extension RENAME dorm_metadata_fk  TO metadata_fk;
ALTER TABLE dorm_extension RENAME dorm_extension_qualifier  TO extension_qualifier;
ALTER TABLE dorm_extension RENAME dorm_name  TO extension_name;
ALTER TABLE dorm_metadata RENAME dorm_qualifier  TO metadata_qualifier;
ALTER TABLE dorm_metadata RENAME dorm_version  TO metadata_version;
ALTER TABLE dorm_metadata RENAME dorm_type  TO metadata_type;
insert into dorm_versionning (version) values (2);


-- UPDATE 3

ALTER TABLE dorm_extension RENAME metadata_fk  TO metadata_id;
insert into dorm_versionning (version) values (3);


-- UPDATE 4

ALTER TABLE dorm_dependencies
   ALTER COLUMN id_parent DROP DEFAULT;
ALTER TABLE dorm_dependencies
   ALTER COLUMN id_child DROP DEFAULT;
ALTER TABLE dorm_extension
   ALTER COLUMN metadata_id DROP DEFAULT;

DROP SEQUENCE dorm_dependencies_id_child_seq;
DROP SEQUENCE dorm_dependencies_id_parent_seq;
DROP SEQUENCE dorm_extension_dorm_metadata_fk_seq;

insert into dorm_versionning (version) values (4);


-- UPDATE 5

ALTER TABLE dorm_metadata DROP COLUMN metadata_type;
insert into dorm_versionning (version) values (5);


-- UPDATE 6

DROP TABLE dorm_extension;

ALTER TABLE dorm_metadata DROP COLUMN metadata_version;
ALTER TABLE dorm_metadata ADD COLUMN extension_name character varying(255);

CREATE TABLE dorm_properties
(
   id serial NOT NULL,
   property_key character varying(255),
   property_value character varying,
   metadata_id integer,
   CONSTRAINT id_primary PRIMARY KEY (id),
   CONSTRAINT metadata_fk FOREIGN KEY (metadata_id) REFERENCES dorm_metadata (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into dorm_versionning (version) values (6);


-- UPDATE 7

ALTER TABLE dorm_metadata RENAME metadata_qualifier  TO metadata_name;
ALTER TABLE dorm_metadata ADD COLUMN metadata_version character varying(255);
insert into dorm_versionning (version) values (7);


-- UPDATE 8

ALTER TABLE dorm_metadata DROP CONSTRAINT unique_qualifier;
insert into dorm_versionning (version) values (8);