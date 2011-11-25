package com.zenika.dorm.core.dao.sql;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.GenericDormMetadata;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormDaoJdbcMapper {

    public static final String ID_COLUMN = "id";
    public static final String PROPERTY_KEY_COLUMN = "property_key";
    public static final String PROPERTY_VALUE_COLUMN = "property_value";
//    public static final String EXTENSION_NAME_COLUMN = "extension_name";
//    public static final String METADATA_QUALIFIER_COLUMN = "metadata_qualifier";

    @Inject
    private static ExtensionFactoryServiceLoader serviceLoader;

    static DormMetadata mapMetadata(ResultSet resultSet) {

        Map<String, String> extensionProperties = new HashMap<String, String>();

        Long id = null;
        try {
            resultSet.next();
            id = resultSet.getLong(ID_COLUMN);
        } catch (SQLException e) {
            throw new CoreException("Unable to parse result set", e);
        }


        return new GenericDormMetadata(id);
    }

    static DormMetadata mapMetadataWithExtension(ResultSet resultSet, String extensionName) {

        Map<String, String> extensionProperties = new HashMap<String, String>();

        Long id = null;

        try {
            while (resultSet.next()) {

                if (resultSet.isFirst()) {
                    id = resultSet.getLong(ID_COLUMN);
                    break;
                }

                extensionProperties.put(resultSet.getString(PROPERTY_KEY_COLUMN), resultSet.getString(PROPERTY_VALUE_COLUMN));
            }
        } catch (SQLException e) {
            throw new CoreException("Unable to parse result set", e);
        }

        return serviceLoader.getInstanceOf(extensionName).fromMap(id, extensionProperties);
    }
}
