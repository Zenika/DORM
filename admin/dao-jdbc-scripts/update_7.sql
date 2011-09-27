ALTER TABLE dorm_metadata RENAME metadata_qualifier  TO metadata_name;
ALTER TABLE dorm_metadata ADD COLUMN metadata_version character varying(255);
