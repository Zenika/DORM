//package com.zenika.dorm.core.test.model.mapper;
//
//import com.zenika.dorm.core.model.DormMetadata;
//import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
//import org.fest.assertions.Assertions;
//import org.junit.Test;
//
//import java.util.Map;
//
///**
// * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
// */
//public class MetadataExtensionMapperUnitTest {
//
//    @Test
//    public void fromExtension() {
//
//        DormMetadata extension = new TestMetadataExtension("testfoo", "testbar");
//
//        Map<String, String> properties = MetadataExtensionMapper.fromExtension(extension);
//
//        Assertions.assertThat(properties.get("foo")).isEqualTo("testfoo");
//        Assertions.assertThat(properties.get("bar")).isNull();
//    }
//
//    /**
//     * Extension for test purposes
//     */
//    public class TestMetadataExtension implements DormMetadata {
//
//        private String foo;
//        private String bar;
//
//        public TestMetadataExtension(String foo, String bar) {
//            this.foo = foo;
//            this.bar = bar;
//        }
//
//        public String getFoo() {
//            return foo;
//        }
//
//        @Override
//        public String getQualifier() {
//            return null;
//        }
//
//        @Override
//        public String getType() {
//            return null;
//        }
//
//        @Override
//        public DormMetadata createFromMap(Map<String, String> properties) {
//            return null;
//        }
//    }
//}
