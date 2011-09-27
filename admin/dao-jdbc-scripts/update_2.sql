ALTER TABLE dorm_dependencies RENAME dorm_usage  TO usage;
ALTER TABLE dorm_extension RENAME dorm_key  TO property_key;
ALTER TABLE dorm_extension RENAME dorm_value  TO property_value;
ALTER TABLE dorm_extension RENAME dorm_metadata_fk  TO metadata_fk;
ALTER TABLE dorm_extension RENAME dorm_extension_qualifier  TO extension_qualifier;
ALTER TABLE dorm_extension RENAME dorm_name  TO extension_name;
ALTER TABLE dorm_metadata RENAME dorm_qualifier  TO metadata_qualifier;
ALTER TABLE dorm_metadata RENAME dorm_version  TO metadata_version;
ALTER TABLE dorm_metadata RENAME dorm_type  TO metadata_type;

