package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenMetadataExtensionUnitTest extends AbstractUnitTest {

    @Test
    public void createMetadataFromProperties() {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(MavenMetadataExtension.METADATA_GROUPID, fixtures.getGroupId());
        properties.put(MavenMetadataExtension.METADATA_ARTIFACTID, fixtures.getArtifactId());
        properties.put(MavenMetadataExtension.METADATA_VERSIONID, fixtures.getVersionId());
        properties.put(MavenMetadataExtension.METADATA_TYPE, fixtures.getType());

        DormMetadataExtension dummyExtension = new MavenMetadataExtension("a", "b", "c", "d");
        DormMetadataExtension extension = dummyExtension.createFromMap(properties);

        Assertions.assertThat(extension).isEqualTo(fixtures.getMetadataExtension());
    }
}
