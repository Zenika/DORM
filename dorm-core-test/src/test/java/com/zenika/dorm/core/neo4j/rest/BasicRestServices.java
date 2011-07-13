package com.zenika.dorm.core.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class BasicRestServices {

    private static final String NODE_ENTRY_POINT_URI = "http://localhost:7474/db/data/node";
    private static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    private static final String INDEX_ENTRY_POINT_URI = "http://localhost:7474/db/data/index";
    private static final String INTERNAL_RELATIONSHIP = "internal_relationship";
    private ObjectMapper mapper;

    public BasicRestServices() {
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public void push(Dependency dependency) throws IOException {
        storeDependency(dependency);
    }

    public void push(Dependency dependency, String parent) throws IOException {
        String dependencyUri = storeDependency(dependency);
        if (dependencyUri != null) {
            String parentUri = getDependencyUri(parent);
            createRelationship(dependencyUri, parentUri, dependency.getUsage());
        }
    }

    private String storeDependency(Dependency dependency) throws IOException {
        String originUri = null;
        String metadataUri = null;
        String dependencyUri = getDependencyUri(dependency.getMetadata().getFullQualifier());
        if (dependencyUri == null) {
            try {
                originUri = postNode(parseOriginProperty(dependency.getMetadata().getOrigin()));
                metadataUri = postNode(parseMetaDataProperty(dependency.getMetadata()));
                dependencyUri = postNode("{}");
                createInternalRelationship(metadataUri, originUri);
                createInternalRelationship(dependencyUri, metadataUri);
                attacheIndexToDependency(dependencyUri, dependency.getMetadata().getFullQualifier(),
                        createIndexForDependency());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return getDependencyUri(dependency.getMetadata().getFullQualifier());
        }
        return dependencyUri;
    }

    private String parseOriginProperty(DormOrigin origin) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("qualifier", origin.getQualifier());
        map.put("origin", origin.getOrigin());
        return mapper.writeValueAsString(map);
    }

    private String parseMetaDataProperty(DormMetadata metadata) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("qualifier", metadata.getQualifier());
        map.put("version", metadata.getVersion());
        map.put("fullQualifier", metadata.getFullQualifier());
        return mapper.writeValueAsString(map);
    }

    private String parseRelationship(String child, Usage usage) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("to", child);
        if (usage != null) {
            map.put("type", usage.getName());
        } else {
            map.put("type", INTERNAL_RELATIONSHIP);
        }
        return mapper.writeValueAsString(map);
    }

    private String parseIndexDependency() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "dependency");
        Map<String, String> configMap = new LinkedHashMap<String, String>();
        configMap.put("type", "fulltext");
        configMap.put("provider", "lucene");
        map.put("config", configMap);
        return mapper.writeValueAsString(map);
    }

    private void createInternalRelationship(String node, String child) throws IOException {
        createRelationship(node, child, null);
    }

    private void createRelationship(String node, String child, Usage usage) throws IOException {
        WebResource resource = Client.create().resource(NODE_ENTRY_POINT_URI + "/" + getNodeId(node) +
                "/relationships");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(parseRelationship(child, usage)).post(ClientResponse.class);
        System.out.println("POST to " + NODE_ENTRY_POINT_URI + getNodeId(node) + "/relationships" +
                ", status code " + response.getStatus() + ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
    }

    private String createIndexForDependency() throws Exception {
        WebResource resource = Client.create().resource(INDEX_ENTRY_POINT_URI + "/node");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(parseIndexDependency()).post(ClientResponse.class);
        System.out.println("POST to " + INDEX_ENTRY_POINT_URI + "/node, status code " + response.getStatus() +
                ", location header " + (response.getLocation() != null ? response.getLocation().toString() : ""));
        if (response.getStatus() == 404 || response.getStatus() == 400) {
            throw new Exception(response.getEntity(String.class));
        }
        return response.getLocation().toString();

    }

    private void attacheIndexToDependency(String dependencyUri, String fullQualifier, String indexUri) throws Exception {
        WebResource resource = Client.create().resource(indexUri + "fullqualifier/" + fullQualifier);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(uriToJson(dependencyUri)).post(ClientResponse.class);
        System.out.println("POST to " + indexUri + "fullqualifier/" + fullQualifier + ", status code " + response.getStatus() + ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
        if (response.getStatus() == 404 || response.getStatus() == 400 || response.getStatus() == 405) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    private String postNode(String jsonProperties) throws Exception {
        WebResource resource = Client.create().resource(NODE_ENTRY_POINT_URI);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(jsonProperties).post(ClientResponse.class);
        System.out.println("POST to " + NODE_ENTRY_POINT_URI + ", status code " + response.getStatus() +
                ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
        if (response.getStatus() == 400 || response.getStatus() == 404) {
            throw new Exception(response.getEntity(String.class));
        }
        return response.getLocation().toString();
    }

    private boolean isDependencyExist(String fullQualifier) {
        WebResource resource = Client.create().resource(INDEX_ENTRY_POINT_URI + "/node/dependency/fullqualifier/" +
                fullQualifier);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        System.out.println("GET to " + INDEX_ENTRY_POINT_URI + "/node/dependency/fullqualifier/" + fullQualifier +
                ", status code " + response.getStatus());
        return response.getStatus() == 404;
    }

    private String getDependencyUri(String fullQualifier) throws IOException {
        WebResource resource = Client.create().resource(INDEX_ENTRY_POINT_URI + "/node/dependency/fullqualifier/" +
                fullQualifier);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        System.out.println("GET to " + INDEX_ENTRY_POINT_URI + "/node/dependency/fullqualifier/" + fullQualifier +
                ", status code " + response.getStatus());
        if (response.getStatus() == 404) {
            return null;
        }
        Map<String, Object> dependencyMap = parseJsonToMap(response.getEntity(String.class));
        if (dependencyMap == null) {
            return null;
        } else {
            return (String) dependencyMap.get("self");
        }
    }

    private String uriToJson(String uri) {
        StringBuilder str = new StringBuilder();
        str.append('\"');
        str.append(uri);
        str.append('\"');
        return str.toString();
    }

    private Map<String, Object> parseJsonToMap(String json) throws IOException {
        if (json.length() < 4) {
            return null;
        }
        return mapper.readValue(json.substring(2, json.length() - 2), Map.class);
    }

    private String getNodeId(String str) {
        return str.split("/")[str.split("/").length - 1];
    }
}
