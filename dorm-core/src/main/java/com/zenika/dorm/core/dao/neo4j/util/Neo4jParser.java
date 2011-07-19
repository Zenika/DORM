package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.DefaultDependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.mapper.OriginMapper;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.*;

import static com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor.METADATA_RELATIONSHIP;
import static com.zenika.dorm.core.dao.neo4j.util.Neo4jRequestExecutor.ORIGIN_RELATIONSHIP;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jParser {


    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public static String parseOriginProperty(DormOrigin origin) throws IOException {
        Map<String, String> properties = OriginMapper.fromOrigin(origin);
        return mapper.writeValueAsString(properties);
    }

    public static String parseMetaDataProperty(DormMetadata metadata) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("qualifier", metadata.getQualifier());
        map.put("version", metadata.getVersion());
        map.put("fullQualifier", metadata.getFullQualifier());
        return mapper.writeValueAsString(map);
    }

    public static String parseRelationship(String child, Usage usage) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("to", child);
        if (usage != null) {
            map.put("type", usage.getName());
        }
        return mapper.writeValueAsString(map);
    }

    public static String parseIndexDependency() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "dependency");
        Map<String, String> configMap = new LinkedHashMap<String, String>();
        configMap.put("type", "fulltext");
        configMap.put("provider", "lucene");
        map.put("config", configMap);
        return mapper.writeValueAsString(map);
    }

    public static String parseTraverse(Usage usage) throws IOException {
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

    public static Map<String, DependencyNode> parseTraverseToDependency(DormMetadata dormMetadata, Usage usage, String traverse)
            throws Exception {
        List<Map<String, Object>> nodesMap = Neo4jParser.parseMultiJsonToMap(traverse);
        Map<String, DependencyNode> dependencyMap = new HashMap<String, DependencyNode>();
        DormOrigin dormOrigin = dormMetadata.getOrigin();
        for (Map<String, Object> map : nodesMap) {
            if (!map.get("type").equals(Neo4jRequestExecutor.ORIGIN_RELATIONSHIP) && !map.get("type")
                    .equals(Neo4jRequestExecutor.METADATA_RELATIONSHIP)) {
                DependencyNode dependencyNodeParent = dependencyMap.get(map.get("start"));
                DependencyNode dependencyNodeChild = dependencyMap.get(map.get("end"));
                if (dependencyNodeParent == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("start"), dormOrigin);
                    Dependency dependencyParent = new DefaultDependency(metadata);
                    dependencyParent.setUsage(usage);
                    dependencyNodeParent = new DefaultDependencyNode(dependencyParent);
                    dependencyMap.put((String) map.get("start"), dependencyNodeParent);
                }
                if (dependencyNodeChild == null) {
                    DormMetadata metadata = getMetadataIntoListMap(nodesMap, (String) map.get("end"), dormOrigin);
                    Dependency dependencyChild = new DefaultDependency(metadata);
                    dependencyChild.setUsage(usage);
                    dependencyNodeChild = new DefaultDependencyNode(dependencyChild);
                    dependencyMap.put((String) map.get("end"), dependencyNodeChild);
                }
                dependencyNodeParent.addChild(dependencyNodeChild);
            }
        }
        return dependencyMap;
    }

    private static DormMetadata getMetadataIntoListMap(List<Map<String, Object>> listMap, String nodeUri, DormOrigin origin)
            throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type")
                    .equals(Neo4jRequestExecutor.METADATA_RELATIONSHIP)) {
                DormOrigin dormOrigin = getOriginIntoListMap(listMap, (String) map.get("end"), origin);
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

    private static DormOrigin getOriginIntoListMap(List<Map<String, Object>> listMap, String nodeUri, DormOrigin origin)
            throws Exception {
        for (Map<String, Object> map : listMap) {
            if (map.get("start").equals(nodeUri) && map.get("type")
                    .equals(Neo4jRequestExecutor.ORIGIN_RELATIONSHIP)) {
                Map<String, String> dormOriginProperties = new Neo4jRequestExecutor()
                        .getPropertiesNode((String) map.get("end"));
                DormOrigin dormOrigin = OriginMapper.toOrigin(origin, dormOriginProperties);
                if (dormOrigin == null) {
                    throw new Exception();
                }
                return dormOrigin;
            }
        }
        return null;
    }

    public static String uriToJson(String uri) {
        StringBuilder str = new StringBuilder();
        str.append('\"');
        str.append(uri);
        str.append('\"');
        return str.toString();
    }

    public static Map<String, Object> parseJsonToMap(String json) throws IOException {
        System.out.println(json);
        if (json.length() < 4) {
            return null;
        } else if (json.charAt(0) != '[') {
            return mapper.readValue(json, Map.class);
        }
        return mapper.readValue(json.substring(2, json.length() - 2), Map.class);
    }

    public static Map<String, String> parseJsonToMapString(String json) throws IOException {
        System.out.println(json);
        if (json.length() < 4) {
            return null;
        }
        return mapper.readValue(json.substring(2, json.length() - 2), Map.class);
    }

    public static List<Map<String, Object>> parseMultiJsonToMap(String json) throws IOException {
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
    }

}
