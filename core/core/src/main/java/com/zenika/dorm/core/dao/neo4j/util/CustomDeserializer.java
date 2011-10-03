package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class CustomDeserializer extends JsonDeserializer<Neo4jMetadata> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomDeserializer.class);

    @Override
    public Neo4jMetadata deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        jp.nextToken();
        Neo4jMetadata metadata = new Neo4jMetadata();
        metadata.setProperties(new HashMap<String, String>());
        while (jp.hasCurrentToken()) {
            JsonToken token = jp.getCurrentToken();
            if (!token.isScalarValue()) {
                if (jp.getText().equals("extensionName")) {
                    jp.nextValue();
                    metadata.setExtensionName(jp.getText());
                } else if (jp.getText().equals("name")) {
                    jp.nextValue();
                    metadata.setName(jp.getText());
                } else if (token.equals(JsonToken.END_OBJECT)){
                    break;
                } else {
                    String key = jp.getText();
                    jp.nextToken();
                    metadata.getProperties().put(key, jp.getText());
                }
            }
            jp.nextToken();
        }
        
        return metadata;
    }


}
