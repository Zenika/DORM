package com.zenika.dorm.core.test.experimental;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.mrbean.MrBeanModule;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Experimental tests with dorm stuff on jackson and REST api
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JacksonExperimentalTest {

    /**
     * Simple interface for test purposes
     */
    public interface DummyJacksonInterfaceTest {

        public String getFoo();

        public String getBar();
    }

    private static final Logger LOG = LoggerFactory.getLogger(JacksonExperimentalTest.class);

    private static final String JSON_VALUES = "{\"foo\":\"testfoo\",\"bar\":\"testbar\"}";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before() {
        mapper.registerModule(new MrBeanModule());
    }

    /**
     * Deserialization process : materialize an interface
     */
    @Test
    public void deserializeInterfaceFromJSON() throws IOException {

        DummyJacksonInterfaceTest test = mapper.readValue(JSON_VALUES, DummyJacksonInterfaceTest.class);

        LOG.trace("Interface foo = " + test.getFoo());
        LOG.trace("Interface bar = " + test.getBar());

        Assertions.assertThat(test.getFoo()).as("Foo").isEqualTo("testfoo");
        Assertions.assertThat(test.getBar()).as("Bar").isEqualTo("testbar");
    }

    @Test
    public void serializeInterfaceFromJSON() throws IOException {

        DummyJacksonInterfaceTest test = new DummyJacksonInterfaceTest() {

            @Override
            public String getFoo() {
                return "testfoo";
            }

            @Override
            public String getBar() {
                return "testbar";
            }
        };

        String json = mapper.writeValueAsString(test);

        LOG.trace("JSON from test = " + json);

        Assertions.assertThat(json).as("JSON from interface").isEqualTo(JSON_VALUES);
    }
}
