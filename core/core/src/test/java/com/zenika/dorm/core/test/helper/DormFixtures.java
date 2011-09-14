package com.zenika.dorm.core.test.helper;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;

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
    private String origin = DefaultDormMetadataExtension.EXTENSION_NAME;

    @Override
    public String getType() {
        return DefaultDormMetadataExtension.EXTENSION_TYPE;
    }

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return new DefaultDormMetadataExtension(name);
    }

    @Override
    public DormWebServiceRequest.Builder getRequestBuilder() {
        return super.getRequestBuilder()
                .property(DefaultDormMetadataExtension.METADATA_NAME, name);
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