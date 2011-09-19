package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import javax.sql.DataSource;

public abstract class JDBCAbstractTask {

    public static final String ID_COLUMN = "id";
    public static final String PROPERTY_KEY_COLUMN = "property_key";
    public static final String PROPERTY_VALUE_COLUMN = "property_value";
    public static final String EXTENSION_NAME_COLUMN = "extension_name";
    public static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";

    @Inject
    protected ExtensionFactoryServiceLoader serviceLoader;

    @Inject
    protected DataSource dataSource;

    public abstract Object execute();


}
