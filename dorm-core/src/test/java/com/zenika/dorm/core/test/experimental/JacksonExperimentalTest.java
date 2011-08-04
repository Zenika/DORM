package com.zenika.dorm.core.test.experimental;

import org.codehaus.jackson.annotate.JsonIgnore;
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
 * - deserialize from json directly to an interface : use of mrbean module
 * - serialize with ignoring composite attributes : use of jackson mixins
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class JacksonExperimentalTest {

    private static final Logger LOG = LoggerFactory.getLogger(JacksonExperimentalTest.class);

    private static final String JSON_VALUES = "{\"foo\":\"testfoo\",\"bar\":\"testbar\"}";

    private ObjectMapper mapper = new ObjectMapper();

    private TestSimpleInterface simpleInterface;

    private TestCompositeInterface compositeInterface;


    @Before
    public void before() {

        simpleInterface = new TestSimpleInterface() {

            @Override
            public String getFoo() {
                return "testfoo";
            }

            @Override
            public String getBar() {
                return "testbar";
            }
        };

        compositeInterface = new TestCompositeInterface() {

            @Override
            public String getTest() {
                return "testtest";
            }

            @Override
            public TestSimpleInterface getInterface() {
                return simpleInterface;
            }
        };
    }

    /**
     * Deserialization process : materialize an interface
     *
     * @throws IOException
     */
    @Test
    public void deserializeJSONToSimpleInterface() throws IOException {

        mapper.registerModule(new MrBeanModule());

        TestSimpleInterface test = mapper.readValue(JSON_VALUES, TestSimpleInterface.class);

        LOG.trace("Interface foo = " + test.getFoo());
        LOG.trace("Interface bar = " + test.getBar());

        Assertions.assertThat(test.getFoo()).as("Foo").isEqualTo("testfoo");
        Assertions.assertThat(test.getBar()).as("Bar").isEqualTo("testbar");
    }

    /**
     * Serialize composite interface into JSON without nested properties
     *
     * @throws IOException
     */
    @Test
    public void serializeCompositeInterfaceWithoutNestedProperties() throws IOException {

        mapper.getSerializationConfig().addMixInAnnotations(TestCompositeInterface.class, TestMixIn.class);

        String json = mapper.writeValueAsString(compositeInterface);
        LOG.trace("Test JSON = " + json);

        Assertions.assertThat(json).as("JSON").isEqualTo("{\"test\":\"testtest\"}");
    }

    /**
     * Simple interface for test purposes
     */
    public static interface TestSimpleInterface {

        public String getFoo();

        public String getBar();
    }

    /**
     * Composite interface which contains the simple interface
     */
    public static interface TestCompositeInterface {

        public String getTest();

        public TestSimpleInterface getInterface();
    }

    /**
     * Mixin for the composite interface, try to ignore the nested attribute
     */
    public static abstract class TestMixIn implements TestCompositeInterface {

        @Override
        @JsonIgnore
        public abstract TestSimpleInterface getInterface();
    }
}
