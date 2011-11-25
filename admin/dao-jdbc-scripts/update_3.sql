ALTER TABLE dorm_extension RENAME metadata_fk  TO metadata_id;

insert into dorm_versionning (version) values (3);