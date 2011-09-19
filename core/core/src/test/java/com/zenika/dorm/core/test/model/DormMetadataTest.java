package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class DormMetadataTest extends DormMetadata{

    private String version;
    private String testField;
    public static final String METADATA_VERSION = "version";
    public static final String METADATA_FIELD = "field";

    public static final String EXTENSION_NAME = "Dorm_test";

    public DormMetadataTest() {
    }

    public DormMetadataTest(String version, String testField) {
        this.version = version;
        this.testField = testField;
    }

    @Override
    public String getName() {
        return "Dorm_test-" + version;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }

    public String getTestField() {
        return testField;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DormMetadataTest");
        sb.append("{extensionName='").append(getExtensionName()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", testField='").append(testField).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
