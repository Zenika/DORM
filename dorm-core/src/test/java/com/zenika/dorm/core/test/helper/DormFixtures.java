package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.DormRequest;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.processor.impl.DormProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Fixtures for the dorm core model and the dorm extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormFixtures extends ExtensionFixtures {

    /**
     * Dorm metadata extension
     */
    private String name = "testname";
    private String qualifier = "testname";
    private String origin = DefaultDormMetadataExtension.NAME;

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return new DefaultDormMetadataExtension(name);
    }

    @Override
    public Map<String, String> getRequestPropertiesForExtension() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(DormRequest.ORIGIN, DefaultDormMetadataExtension.NAME);
        properties.put(DormProcessor.METADATA_NAME, name);
        return properties;
    }

    public String getName() {
        return name;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getOrigin() {
        return origin;
    }
}
