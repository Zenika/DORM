SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type, m.metadata_qualifier
 FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_fk = m.id WHERE m.id = 
 	(SELECT metadata_fk FROM dorm_extension 
 	WHERE property_key = 'name' AND property_value = 'habi-base')

SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type
FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_fk = m.id
WHERE m.id IN (SELECT metadata_fk FROM dorm_extension WHERE e.property_key = 'versionId' AND e.property_value = '1.0.0')

SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type
FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_fk = m.id
WHERE m.id = ANY (SELECT metadata_fk FROM dorm_extension WHERE e.property_key = 'versionId' AND e.property_value = '1.0.0')

SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type
FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_fk = m.id
WHERE m.id IN (SELECT metadata_fk FROM dorm_extension 
	WHERE ((property_key = 'versionId' AND property_key = 'groupId') 
	AND (property_value = '1.0.0' AND property_value = 'test')))

SELECT e.property_key, e.property_value, e.extension_qualifier, e.extension_name, m.metadata_version, m.metadata_type
FROM dorm_metadata m JOIN dorm_extension e ON e.metadata_fk = m.id
WHERE m.id IN (select * from dorm_metadata m
join dorm_extension e1 on (m.id = e1.metadata_id and e1.property_key = 'versionId' and e1.property_value = '1.0.0')
join dorm_extension e2 on (m.id = e2.metadata_id and e2.property_key = 'groupId' and e2.property_value = 'test'))
