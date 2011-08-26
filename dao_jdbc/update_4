ALTER TABLE dorm_dependencies
   ALTER COLUMN id_parent DROP DEFAULT;
ALTER TABLE dorm_dependencies
   ALTER COLUMN id_child DROP DEFAULT;
ALTER TABLE dorm_extension
   ALTER COLUMN metadata_id DROP DEFAULT;

DROP SEQUENCE dorm_dependencies_id_child_seq;
DROP SEQUENCE dorm_dependencies_id_parent_seq;
DROP SEQUENCE dorm_extension_dorm_metadata_fk_seq;
