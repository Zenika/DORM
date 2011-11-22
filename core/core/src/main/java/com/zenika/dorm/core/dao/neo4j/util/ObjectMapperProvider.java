package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;

    public ObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    public static ObjectMapper createDefaultMapper() {
        SimpleModule module = new SimpleModule("Dorm", new Version(1, 0 , 0, null));
        module.addDeserializer(Neo4jMetadata.class, new CustomDeserializer());
        module.addSerializer(Neo4jMetadata.class, new CustomSerializer());
        ObjectMapper result = new ObjectMapper();
        result.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, true);
        result.registerModule(module);
        return result;
    }
}
