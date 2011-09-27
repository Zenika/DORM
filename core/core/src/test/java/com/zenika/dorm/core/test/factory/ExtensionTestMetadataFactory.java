package com.zenika.dorm.core.test.factory;

import com.zenika.dorm.core.factory.ExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.model.DormMetadataTest;

import java.util.HashMap;
import java.util.Map;

import static com.zenika.dorm.core.test.model.DormMetadataTest.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ExtensionTestMetadataFactory implements ExtensionMetadataFactory {

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    @Override
    public DormMetadata fromMap(Long id, Map<String, String> properties) {
        return new DormMetadataTest(
                id,
                properties.get(VERSION_FIELD),
                properties.get(FIELD_FIELD),
                properties.get(DATA_FIELD));
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        DormMetadataTest metadataTest = (DormMetadataTest) metadata;

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(VERSION_FIELD, metadataTest.getVersion());
        properties.put(FIELD_FIELD, metadataTest.getField());
        properties.put(DATA_FIELD, metadataTest.getData());
        return properties;
    }
}
