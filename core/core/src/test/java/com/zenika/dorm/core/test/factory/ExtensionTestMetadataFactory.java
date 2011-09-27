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
                properties.get(METADATA_VERSION),
                properties.get(METADATA_FIELD));
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        DormMetadataTest metadataTest = (DormMetadataTest) metadata;
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(METADATA_VERSION, metadataTest.getVersion());
        properties.put(METADATA_FIELD, ((DormMetadataTest) metadata).getTestField());
        return properties;
    }
}
