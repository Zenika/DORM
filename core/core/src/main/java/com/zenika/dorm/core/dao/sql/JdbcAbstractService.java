package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.factory.MetadataExtensionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class JdbcAbstractService {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcAbstractService.class);

    public static final String ID_COLUMN = "id";
    public static final String ID_CHILD_COLUMN = "id_child";
    public static final String ID_PARENT_COLUMN = "id_parent";
    public static final String USAGE_COLUMN = "usage";
    public static final String PROPERTY_KEY_COLUMN = "property_key";
    public static final String PROPERTY_VALUE_COLUMN = "property_value";
    public static final String EXTENSION_QUALIFIER_COLUMN = "extension_qualifier";
    public static final String EXTENSION_NAME_COLUMN = "extension_name";
    public static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";
    public static final String METADATA_VERSION_COLUMN = "metadata_version";
    public static final String METADATA_TYPE_COLUMN = "metadata_type";

    @Inject
    protected MetadataExtensionFactory metadataExtensionFactory;

    @Inject
    protected DataSource dataSource;

    public JdbcAbstractService(){

    }

    public JdbcAbstractService(DataSource dataSource) {
        if (null == dataSource) {
            throw new CoreException("DataSource is null");
        }
        this.dataSource = dataSource;
    }

    public abstract void execute();


}
