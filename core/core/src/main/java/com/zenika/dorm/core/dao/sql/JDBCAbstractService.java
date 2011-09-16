package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.factory.DormMetadataFactory;

import javax.sql.DataSource;

public abstract class JDBCAbstractService {

    public static final String ID_COLUMN = "id";
    public static final String PROPERTY_KEY_COLUMN = "property_key";
    public static final String PROPERTY_VALUE_COLUMN = "property_value";
    public static final String EXTENSION_NAME_COLUMN = "extension_name";
    public static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";
    public static final String METADATA_VERSION_COLUMN = "metadata_version";

//    @Inject
//    protected DormMetadataFactory dormMetadataFactory;

    @Inject
    protected DataSource dataSource;

    public abstract Object execute();


}
