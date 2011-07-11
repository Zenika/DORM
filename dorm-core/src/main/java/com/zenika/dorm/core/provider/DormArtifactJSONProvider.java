package com.zenika.dorm.core.provider;//package com.zenika.dorm.core.ws.rs.provider;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.ext.MessageBodyReader;
//import javax.ws.rs.ext.MessageBodyWriter;
//
//import com.zenika.dorm.core.ws.rs.vo.DormArtifactResult;
//import com.zenika.dorm.core.ws.rs.vo.DormArtifactsResult;
//
//public class DormArtifactJSONProvider implements MessageBodyWriter, MessageBodyReader<DormArtifactsResult>{
//
//	    @Override
//	    public long getSize(Object obj, Class type, Type genericType,
//	                        Annotation[] annotations, MediaType mediaType) {
//	        //Returns -1 (can't be determined in advance)
//	        return -1;
//	    }
//
//	    @Override
//	    public boolean isWriteable(Class type, Type genericType,
//	                               Annotation annotations[], MediaType mediaType) {
//	        return true;
//	    }
//
//	    @Override
//	    public void writeTo(Object target, Class type, Type genericType,
//	                        Annotation[] annotations, MediaType mediaType,
//	                        MultivaluedMap httpHeaders, OutputStream outputStream)
//	            throws IOException {
//
//	        //Use Jackson to build the json output and write it in the outputStream object
//	        objectMapper objectMapper = new ObjectMapper();
//	        Object entity = target;
//
//	        if (type == DormArtifactResult.class) {
//	            objectMapper = objectMapper.enableDefaultTyping();
//	            entity = ((DormArtifactResult) target).getArtifact();
//	        }
//	        if (type == DormArtifactsResult.class) {
//	            objectMapper = objectMapper.enableDefaultTyping();
//	            entity = ((DormArtifactsResult) target).getArtifacts();
//	        }
//
//	        objectMapper.writeValue(outputStream, entity);
//	    }
//
//	    @Override
//	    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
//	        return (type == DormArtifactResult.class || type == DormArtifactsResult.class);
//	    }
//
//	    @Override
//	    public DormArtifactsResult readFrom(Class<DormArtifactsResult> type,
//	                                       Type genericType,
//	                                       Annotation[] annotations,
//	                                       MediaType mediaType,
//	                                       MultivaluedMap<String, String> httpHeaders,
//	                                       InputStream entityStream) throws IOException, WebApplicationException {
//	        return new ObjectMapper().enableDefaultTyping().readValue(entityStream, type);
//	    }
//	}
//
//}
