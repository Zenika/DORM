package com.zenika.dorm.core.test.model;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.test.unit.AbstractUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormMetadataExtensionUnitTest extends AbstractUnitTest {

    @Test
    public void createValidExtension() {

        DormMetadataExtension extension = new DefaultDormMetadataExtension(fixtures.getName());

        // dorm extension qualifier is only the name
        Assertions.assertThat(extension.getQualifier()).isEqualTo(fixtures.getName());
        Assertions.assertThat(extension.getExtensionName()).isEqualTo(fixtures.getOrigin());
    }

    @Test
    public void createMetadataFromProperties() {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put(DefaultDormMetadataExtension.METADATA_NAME, fixtures.getName());

        DormMetadataExtension dummyExtension = new DefaultDormMetadataExtension("fake");
        DormMetadataExtension extension = dummyExtension.createFromMap(properties);

        Assertions.assertThat(extension).isEqualTo(fixtures.getMetadataExtension());
    }
}
