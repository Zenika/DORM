DELETE FROM dorm_extension;
ALTER TABLE dorm_extension ADD COLUMN dorm_extension_qualifier character varying(255) NOT NULL;
ALTER TABLE dorm_extension ADD COLUMN dorm_name character varying(255) NOT NULL;

insert into dorm_versionning (version) values (1);