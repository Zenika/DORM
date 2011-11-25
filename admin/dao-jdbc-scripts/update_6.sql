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