package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class Neo4jExtensionReader implements MessageBodyReader<Neo4jMetadataExtension> {


    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.equals(Neo4jMetadataExtension.class);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Neo4jMetadataExtension readFrom(Class<Neo4jMetadataExtension> type, Type genericType,
                                           Annotation[] annotations, MediaType mediaType,
                                           MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> properties = mapper.readValue(entityStream, Map.class);
        Neo4jMetadataExtension extension = new Neo4jMetadataExtension();
//        extension.setProperties(properties);
        return extension;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private String readAsString(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream);
        StringBuilder sb = new StringBuilder();
        char[] buff = new char[1024];
        int l;
        while ((l = reader.read(buff)) != -1){
            sb.append(buff, 0, l);
        }
        return sb.toString();
    }
}