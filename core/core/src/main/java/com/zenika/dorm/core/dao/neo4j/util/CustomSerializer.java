package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class CustomSerializer extends JsonSerializer<Neo4jMetadata>{

    @Override
    public void serialize(Neo4jMetadata value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
//        jgen.writeString("!!!!!!!!!!!!!!");
        jgen.writeFieldName("extensionName");
        jgen.writeString(value.getExtensionName());
        jgen.writeFieldName("version");
        jgen.writeString(value.getVersion());
        jgen.writeFieldName("name");
        jgen.writeString(value.getName());

        for (Map.Entry<String, String> property : value.getProperties().entrySet()){
            jgen.writeFieldName(property.getKey());
            jgen.writeString(property.getValue());
        }
        jgen.writeEndObject();
    }
}
