package com.zenika.dorm.core.test.model.mapper;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MetadataExtensionMapperUnitTest {

    @Test
    public void fromOrigin() {

        // DefaultDormOrigin has one attribute "name" which will contains "foo"
        DormMetadataExtension extension = new DefaultDormMetadataExtension("foo");

        Map<String, String> properties = MetadataExtensionMapper.fromExtension(extension);

        Assertions.assertThat(properties.get("name")).isEqualTo("foo");
    }

    /**
     * Should fail because DefaultDormOrigin is immutable
     */
    @Test
    public void toOrigin() {

        // create null origin with null attribute "name"
        DormMetadataExtension extension = new DefaultDormMetadataExtension();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("name", "foo");

        MetadataExtensionMapper.toExtension(extension, properties);

        Assertions.assertThat(((DefaultDormMetadataExtension) extension).getName()).isEqualTo("foo");
    }
}
