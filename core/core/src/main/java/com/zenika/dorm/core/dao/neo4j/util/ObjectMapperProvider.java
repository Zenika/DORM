package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defautltObjectMapper;

    public ObjectMapperProvider() {
        defautltObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defautltObjectMapper;
    }

    public static ObjectMapper createDefaultMapper() {
        AnnotationIntrospector.Pair combinedIntrospector = createJaxbJacksonAnnotationIntrospector();
        SimpleModule module = new SimpleModule("Dorm", new Version(1, 0 , 0, null));
        module.addDeserializer(Neo4jMetadata.class, new CustomDeserializer());
        module.addSerializer(Neo4jMetadata.class, new CustomSerializer());
        ObjectMapper result = new ObjectMapper();
        result.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        result.registerModule(module);

//        result.getDeserializationConfig().setAnnotationIntrospector(combinedIntrospector);
//        result.getSerializationConfig().setAnnotationIntrospector(combinedIntrospector);
        return result;
    }

    private static AnnotationIntrospector.Pair createJaxbJacksonAnnotationIntrospector() {

        AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector();
        AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

        return new AnnotationIntrospector.Pair(jaxbIntrospector, jacksonIntrospector);
    }
}
