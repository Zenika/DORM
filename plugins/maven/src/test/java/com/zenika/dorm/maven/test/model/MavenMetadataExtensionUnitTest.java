package com.zenika.dorm.maven.test.model;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.maven.model.MavenMetadata;
import com.zenika.dorm.maven.model.builder.MavenMetadataBuilder;
import com.zenika.dorm.maven.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class MavenMetadataExtensionUnitTest extends AbstractUnitTest {

    @Test
    public void createMetadataFromProperties() {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(MavenMetadata.METADATA_GROUPID, fixtures.getGroupId());
        properties.put(MavenMetadata.METADATA_ARTIFACTID, fixtures.getArtifactId());
        properties.put(MavenMetadata.METADATA_VERSION, fixtures.getMavenVersion());

        DormMetadataExtension dummyExtension = new MavenMetadataBuilder("a")
                .groupId("b")
                .version("c")
                .build();

        DormMetadataExtension extension = dummyExtension.createFromMap(properties);

        Assertions.assertThat(extension).isEqualTo(fixtures.getMetadataExtension());
    }
}
