package com.zenika.dorm.core.test.factory;

import com.zenika.dorm.core.factory.PluginExtensionMetadataFactory;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.test.model.DormMetadataTest;

import java.util.HashMap;
import java.util.Map;

import static com.zenika.dorm.core.test.model.DormMetadataTest.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class PluginExtensionTestMetadataFactory implements PluginExtensionMetadataFactory {

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    @Override
    public DormMetadata fromMap(Long id, Map<String, String> properties) {
        return new DormMetadataTest(
                id,
                properties.get(VERSION_FIELD),
                properties.get(ARTIFACT_ID));
    }

    @Override
    public Map<String, String> toMap(DormMetadata metadata) {
        DormMetadataTest metadataTest = (DormMetadataTest) metadata;

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(VERSION_FIELD, metadataTest.getVersion());
        properties.put(ARTIFACT_ID, metadataTest.getArtifactId());
        return properties;
    }
}
