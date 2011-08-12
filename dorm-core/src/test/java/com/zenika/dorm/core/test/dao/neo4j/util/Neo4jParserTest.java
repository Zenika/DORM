package com.zenika.dorm.core.test.dao.neo4j.util;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormMetadataExtension;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jParserTest {

    private static Logger logger = Logger.getLogger(Neo4jParserTest.class.getName());

    private DormMetadata dormMetadata;
    private DormMetadataExtension origin;

    @Before
    public void setUp(){
        origin = new DefaultDormMetadataExtension("DEFAULT");
        dormMetadata = DefaultDormMetadata.create("1.0.0", "jar", origin);

    }

    @Test
    public void testParseMetaDataProperty(){
//            Map<String, String> map = Neo4jParser.parseMetaDataPropertyToMap(dormMetadata);
//            assertTrue(map.containsKey("qualifier"));
//            assertTrue(map.containsKey("version"));
//            assertTrue(map.containsKey("fullQualifier"));
//
//            assertEquals(dormMetadata.getFullQualifier(), map.get("fullQualifier"));
//            assertEquals(dormMetadata.getQualifier(), map.get("qualifier"));
//            assertEquals(dormMetadata.getVersion(), map.get("version"));
    }
}
