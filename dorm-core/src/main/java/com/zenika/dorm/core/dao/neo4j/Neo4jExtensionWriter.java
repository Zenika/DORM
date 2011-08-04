package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Produces(MediaType.APPLICATION_JSON)
@Provider
public class Neo4jExtensionWriter implements MessageBodyWriter<Neo4jMetadataExtension>{


    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getSize(Neo4jMetadataExtension extension, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeTo(Neo4jMetadataExtension extension, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        Map<String, String> properties = MetadataExtensionMapper.fromExtension(extension);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(entityStream, properties);
    }
}