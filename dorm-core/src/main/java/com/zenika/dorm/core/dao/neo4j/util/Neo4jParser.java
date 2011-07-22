package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jParser {


    private ObjectMapper mapper;

    public Neo4jParser() {
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public String parseOriginProperty(DormMetadataExtension origin) throws IOException {
        Map<String, String> properties = MetadataExtensionMapper.fromOrigin(origin);
        return mapper.writeValueAsString(properties);
    }

    public Map<String, String> parseOriginPropertyToMap(DormMetadataExtension origin) throws IOException {
        return MetadataExtensionMapper.fromOrigin(origin);

    }

    public String parseMetaDataProperty(DormMetadata metadata) throws IOException {
        return mapper.writeValueAsString(parseMetaDataPropertyToMap(metadata));
    }

    public Map<String, String> parseMetaDataPropertyToMap(DormMetadata metadata) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("qualifier", metadata.getQualifier());
        map.put("version", metadata.getVersion());
        map.put("fullQualifier", metadata.getFullQualifier());
        return map;
    }

    public String parseRelationship(String child, Usage usage) throws IOException {
        return mapper.writeValueAsString(parseRelationshipToMap(child, usage));
    }

    public Map<String, String> parseRelationshipToMap(String child, Usage usage) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("to", child);
        if (usage != null) {
            map.put("type", usage.getName());
        }
        return map;
    }

    public String parseIndexDependency() throws IOException {
        return mapper.writeValueAsString(parseIndexDependencyToMap());
    }

    public Map<String, Object> parseIndexDependencyToMap() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "dependency");
        Map<String, String> configMap = new LinkedHashMap<String, String>();
        configMap.put("type", "fulltext");
        configMap.put("provider", "lucene");
        map.put("config", configMap);
        return map;
    }

    public String parseTraverse(Usage usage) throws IOException {
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
        usageRelationship.put("type", usage.getName());
        usageRelationship.put("direction", "out");
        relationships.add(metadataRelationship);
        relationships.add(usageRelationship);
        relationships.add(originRelationship);
        traverseMap.put("relationships", relationships);

        return mapper.writeValueAsString(traverseMap);
    }

    public Map<String, DependencyNode> parseTraverseToDependency(DormMetadata dormMetadata, Usage usage, String traverse)
            throws Exception {
        List<Map<String, Object>> nodesMap = this.parseMultiJsonToMap(traverse);
        Map<String, DependencyNode> dependencyMap = new HashMap<String, DependencyNode>();
        DormMetadataExtension dormOrigin = dormMetadata.getExtension();
        for (Map<String, Object> map : nodesMap) {
            if (!map.get("type").equals(Neo4jRequestExecutor.ORIGIN_RELATIONSHIP) && !map.get("type")
                    .equals(Neo4jRequestExecutor.METADATA_RELATIONSHIP)) {
                DependencyNode dependencyNodeParent = dependencyMap.get(map.get("start"));
                DependencyNode dependencyNodeChild = dependencyMap.get(map.get("end"));
                if (dependencyNodeParent == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("start"), dormOrigin);
                    Dependency dependencyParent = DefaultDependency.create(metadata, usage);
                    dependencyNodeParent = new DefaultDependencyNode(dependencyParent);
                    dependencyMap.put((String) map.get("start"), dependencyNodeParent);
                }
                if (dependencyNodeChild == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("end"), dormOrigin);
                    Dependency dependencyChild = DefaultDependency.create(metadata, usage);
                    dependencyNodeChild = new DefaultDependencyNode(dependencyChild);
                    dependencyMap.put((String) map.get("end"), dependencyNodeChild);
                }
                dependencyNodeParent.addChild(dependencyNodeChild);
            }
        }
        return dependencyMap;
    }

    private DormMetadata getMetadataIntoListMap(List<Map<String, Object>> listMap, String nodeUri, DormMetadataExtension origin)
            throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type")
                    .equals(Neo4jRequestExecutor.METADATA_RELATIONSHIP)) {
                DormMetadataExtension dormOrigin = getOriginIntoListMap(listMap, (String) map.get("end"), origin);
                Map<String, String> dormMetadataMap = new Neo4jRequestExecutor()
                        .getPropertiesNode((String) map.get("end"));
                DormMetadata metadata = null;
                try {
                    metadata = new DefaultDormMetadata(dormMetadataMap.get("version"), dormOrigin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return metadata;
            }
        }
        return null;
    }

    private DormMetadataExtension getOriginIntoListMap(List<Map<String, Object>> listMap, String nodeUri, DormMetadataExtension origin)
            throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type")
                    .equals(Neo4jRequestExecutor.ORIGIN_RELATIONSHIP)) {
                Map<String, String> dormOriginProperties = new Neo4jRequestExecutor()
                        .getPropertiesNode((String) map.get("end"));
                DormMetadataExtension dormOrigin = MetadataExtensionMapper.toOrigin(origin, dormOriginProperties);
                if (dormOrigin == null) {
                    throw new Exception();
                }
                return dormOrigin;
            }
        }
        return null;
    }

    public String parseDependencyToJson(Dependency dependency) throws IOException {
        List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();
        requests.add(createJsonRequest("POST", "/node", 0,
                parseOriginPropertyToMap(dependency.getMetadata().getExtension())));
        requests.add(createJsonRequest("POST", "/node", 1,
                parseMetaDataPropertyToMap(dependency.getMetadata())));
        requests.add(createJsonRequest("POST", "/node", 2, new HashMap<String, String>()));
        requests.add(createJsonRequest("POST", "{" + 1 + "}/relationships", 3,
                parseRelationshipToMap("{" + 0 + "}", dependency.getUsage())));
        requests.add(createJsonRequest("POST", "{" + 2 + "}/relationships", 4,
                parseRelationshipToMap("{" + 1 + "}", dependency.getUsage())));
        requests.add(createJsonRequest("POST", "/index/node", 5,
                parseIndexDependencyToMap()));
        requests.add(createJsonRequest("POST", "/index/node/dependency/fullqualifier/" +
                dependency.getMetadata().getFullQualifier(), 6, "{" + 2 + "}"));
        return mapper.writeValueAsString(requests);
    }

    public String getDependencyUriFromBatchResponse(String response) throws IOException {
        List<Map<String, Object>> list = mapper.readValue(response, List.class);
        for (Map<String, Object> map : list) {
            if ((Integer) map.get("id") == 2) {
                return (String) map.get("location");
            }
        }
        return null;
    }

    public String uriToJson(String uri) {
        StringBuilder str = new StringBuilder();
        str.append('\"');
        str.append(uri);
        str.append('\"');
        return str.toString();
    }

    public Map parseJsonToMap(String json) throws IOException {
        if (json.length() < 4) {
            return null;
        } else if (json.charAt(0) != '[') {
            return mapper.readValue(json, Map.class);
        }
        return mapper.readValue(json.substring(2, json.length() - 2), Map.class);
    }


    public List<Map<String, Object>> parseMultiJsonToMap(String json) throws IOException {
        if (json.length() < 4) {
            return null;
        }
        return mapper.readValue(json, List.class);
    }

    public Map<String, Object> createJsonRequest(String method, String uri, int id, Object body) {
        Map<String, Object> request = new HashMap<String, Object>();
        request.put("method", method);
        request.put("to", uri);
        request.put("id", id);
        request.put("body", body);
        return request;
    }

}
