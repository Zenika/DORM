package com.zenika.dorm.artifactoryimporter;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Scanner;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
@Consumes({
        "application/vnd.org.jfrog.artifactory.storage.FolderInfo+json"
})
public class ArtifactoryProvider<T> implements MessageBodyReader<T>{

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        Scanner scanner = new Scanner(entityStream);
        StringBuilder builder = new StringBuilder(256);
        while (scanner.hasNext()){
            builder.append(scanner.nextLine());
        }
        System.out.println(builder);
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        return mapper.readValue(builder.toString(), type);
    }
}
