package com.zenika.dorm.core.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class BasicRestServices {

    private static final String NODE_ENTRY_POINT_URI = "http://localhost:7474/db/data/node";
    private static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    private static final String INDEX_ENTRY_POINT_URI = "http://localhost:7474/db/data/index";
    private static final String METADATA_RELATIONSHIP = "metadata_relationship";
    private static final String ORIGIN_RELATIONSHIP = "origin_relationship";
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
            createRelationship(parentUri, dependencyUri, dependency.getUsage());
        }
    }

    public DependencyNode getDependencies(Dependency dependency) throws Exception {
        Map<String, Object> traverseMap = new HashMap<String, Object>();
        traverseMap.put("order", "depth_first");
        traverseMap.put("uniqueness", "node");

        Map<String, Object> pruneEvaluatorMap = new HashMap<String, Object>();
        pruneEvaluatorMap.put("language", "builtin");
        pruneEvaluatorMap.put("name", "none");
        traverseMap.put("prune_evaluator", pruneEvaluatorMap);

        List<Map<String, Object>> relationships = new ArrayList<Map<String, Object>>();
        Map<String, Object> metadataRelationship = new HashMap<String, Object>();
        metadataRelationship.put("type", METADATA_RELATIONSHIP);

        Map<String, Object> originRelationship = new HashMap<String, Object>();
        originRelationship.put("type", ORIGIN_RELATIONSHIP);

        Map<String, Object> usageRelationship = new HashMap<String, Object>();
        usageRelationship.put("type", dependency.getUsage().getName());
        usageRelationship.put("direction", "out");
        relationships.add(metadataRelationship);
        relationships.add(usageRelationship);
        relationships.add(originRelationship);
        traverseMap.put("relationships", relationships);

//        Map<String, Object> filterMap = new HashMap<String, Object>();
//        filterMap.put("language", "builtin");
//        filterMap.put("name", "all");
//        traverseMap.put("return_filter", filterMap);

//        traverseMap.put("max_depth", 10);

        String traverseJson = mapper.writeValueAsString(traverseMap);
        String dependencyUri = getDependencyUri(dependency.getMetadata().getFullQualifier());

        System.out.println("Json : " + traverseJson);

        WebResource resource = Client.create().resource(dependencyUri + "/traverse/relationship");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(traverseJson).post(ClientResponse.class);
        System.out.println("POST to " + dependencyUri + "/traverse/relationship" + ", status code " +
                response.getStatus() + ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
        
        List<Map<String, Object>> nodesMap = parseMultiJsonToMap(response.getEntity(String.class));
        Map<String, DependencyNode> dependencyMap = new HashMap<String, DependencyNode>();
        Usage usage = dependency.getUsage();
        for (Map<String, Object> map : nodesMap) {
            System.out.println("START : " + map.get("start"));
            System.out.println("TYPE : " + map.get("type"));
            if (!map.get("type").equals(ORIGIN_RELATIONSHIP) && !map.get("type").equals(METADATA_RELATIONSHIP)) {
                DependencyNode dependencyNodeParent = dependencyMap.get(map.get("start"));
                DependencyNode dependencyNodeChild = dependencyMap.get(map.get("end"));
                if (dependencyNodeParent == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("start"));
                    Dependency dependencyParent = new DefaultDependency(metadata);
                    dependencyParent.setUsage(usage);
                    dependencyNodeParent = new DefaultDependencyNode(dependencyParent);
                    dependencyMap.put((String) map.get("start"), dependencyNodeParent);
                }
                if (dependencyNodeChild == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("end"));
                    Dependency dependencyChild = new DefaultDependency(metadata);
                    dependencyChild.setUsage(usage);
                    dependencyNodeChild = new DefaultDependencyNode(dependencyChild);
                    dependencyMap.put((String) map.get("end"), dependencyNodeChild);
                }
                dependencyNodeParent.addChild(dependencyNodeChild);
            }
            System.out.println("END : " + map.get("end"));
        }
        return dependencyMap.get(dependencyUri);
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
                createInternalRelationship(metadataUri, originUri, ORIGIN_RELATIONSHIP);
                createInternalRelationship(dependencyUri, metadataUri, METADATA_RELATIONSHIP);
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

    public DormMetadata getMetadataIntoListMap(List<Map<String, Object>> listMap, String nodeUri) throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type").equals(METADATA_RELATIONSHIP)) {
                DormOrigin dormOrigin = getOriginIntoListMap(listMap, (String) map.get("end"));
                Map<String, Object> dormMetadataMap = getPropertiesNode((String) map.get("end"));
                DormMetadata metadata = null;
                try {
                     metadata = new DefaultDormMetadata((String) dormMetadataMap.get("version"), dormOrigin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return metadata;
            }
        }
        return null;
    }

    public DormOrigin getOriginIntoListMap(List<Map<String, Object>> listMap, String nodeUri) throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type").equals(ORIGIN_RELATIONSHIP)) {
                Map<String, Object> dormOriginProperties = getPropertiesNode((String) map.get("end"));
                DormOrigin dormOrigin = new DefaultDormOrigin((String) dormOriginProperties.get("qualifier"));
                if (dormOrigin == null) {
                    throw new Exception();
                }
                return dormOrigin;
            }
        }
        return null;
    }

    public Map<String, Object> getPropertiesNode(String nodeUri) throws IOException {
        WebResource resource = Client.create().resource(nodeUri + "/properties");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        System.out.println("GET to " + nodeUri + "/properties" + ", status code " +
                response.getStatus() + ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
        return parseJsonToMap(response.getEntity(String.class));
    }

    private void createInternalRelationship(String node, String child, String name) throws IOException {
        createRelationship(node, child, new Usage(name));
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
        System.out.println("POST to " + indexUri + "fullqualifier/" + fullQualifier + ", status code " +
                response.getStatus() + ", location header " +
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
        } else if (response.getStatus() == 500) {
            System.out.println(response.getEntity(String.class));
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
        System.out.println(json);
        if (json.length() < 4) {
            return null;
        } else if (json.charAt(0) != '[') {
            return mapper.readValue(json, Map.class);
        }
        return mapper.readValue(json.substring(2, json.length() - 2), Map.class);
    }

    private List<Map<String, Object>> parseMultiJsonToMap(String json) throws IOException {
//        System.out.println(json);
        if (json.length() < 4) {
            return null;
        }
        List<Map<String, Object>> nodesMap = new ArrayList<Map<String, Object>>();
        json = json.substring(2, json.length() - 2);
        String[] nodesJson = json.split(", \\{");
        nodesMap.add(mapper.readValue(nodesJson[0], Map.class));
        if (nodesJson.length > 0) {
            for (int i = 1; i < nodesJson.length; i++) {
                nodesMap.add(mapper.readValue("{" + nodesJson[i], Map.class));
            }
        }
        return nodesMap;
//        System.out.println(json);
//        return null;
    }

    private String getNodeId(String str) {
        return str.split("/")[str.split("/").length - 1];
    }
}
