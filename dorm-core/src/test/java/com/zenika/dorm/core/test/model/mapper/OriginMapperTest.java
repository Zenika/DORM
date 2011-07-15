package com.zenika.dorm.core.test.model.mapper;

import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import com.zenika.dorm.core.model.mapper.OriginMapper;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class OriginMapperTest {

    @Test
    public void fromOrigin() {

        // DefaultDormOrigin has one attribute "name" which will contains "foo"
        DormOrigin origin = new DefaultDormOrigin("foo");

        Map<String, String> properties = OriginMapper.fromOrigin(origin);

        Assertions.assertThat(properties.get("name")).isEqualTo("foo");
    }

    @Test
    public void toOrigin() {

        // create null origin with null attribute "name"
        DormOrigin origin = new DefaultDormOrigin();

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("name", "foo");

        OriginMapper.toOrigin(origin, properties);

        Assertions.assertThat(((DefaultDormOrigin) origin).getName()).isEqualTo("foo");
    }
}
