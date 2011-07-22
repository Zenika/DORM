package com.zenika.dorm.maven.test.helper;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.test.helper.ExtensionFixtures;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFixtures extends ExtensionFixtures {


    /**
     * Maven metadata extension
     */
    private String groupId = "testgroup";
    private String artifactId = "testartifact";
    private String versionId = "testversion";
    private String type = "jar";
    private String origin = MavenMetadataExtension.ORIGIN;

    @Override
    public DormMetadataExtension getMetadataExtension() {
        return new MavenMetadataExtension(groupId, artifactId, versionId, type);
    }

    @Override
    public Map<String, String> getRequestPropertiesForExtension() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(MavenProcessor.METADATA_GROUPID, groupId);
        properties.put(MavenProcessor.METADATA_ARTIFACTID, artifactId);
        properties.put(MavenProcessor.METADATA_VERSIONID, versionId);
        return properties;
    }
}
