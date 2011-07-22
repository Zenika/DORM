package com.zenika.dorm.core.dao.neo4j.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zenika.dorm.core.dao.neo4j.Neo4jNode;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;
import com.zenika.dorm.core.graph.impl.Usage;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jRequestExecutor {

    private static Logger logger = Logger.getLogger(Neo4jRequestExecutor.class.getName());

    public static final String NODE_ENTRY_POINT_URI = "http://localhost:7474/db/data/node";
    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    public static final String INDEX_ENTRY_POINT_URI = "http://localhost:7474/db/data/index";
    public static final String METADATA_RELATIONSHIP = "metadata_relationship";
    public static final String ORIGIN_RELATIONSHIP = "origin_relationship";
    public static final String BATCH_URI = "http://localhost:7474/db/data/batch";

    private Neo4jParser parser;
    private WebResource resource;
    private ObjectMapper mapper;

    public Neo4jRequestExecutor() {
        parser = new Neo4jParser();

        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JAXBContentResolver.class);
        Client client = Client.create(config);
        resource = client.resource(DATA_ENTRY_POINT_URI);
    }

    public <T extends Neo4jNode> T post(T node) {
        node.setResponse(resource.path("node").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(node).post(Neo4jResponse.class));
        return node;
    }

    public <T extends Neo4jNode> T get(URI uri){
        Neo4jResponse response = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(Neo4jResponse.class);
        ClientResponse clientResponse = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        System.out.println(response);
        System.out.println(clientResponse.getEntity(String.class));
        return null;
    }

    public void createRelationship(String node, String child, Usage usage) throws IOException {
        WebResource resource = Client.create().resource(NODE_ENTRY_POINT_URI + "/" + getNodeId(node) +
                "/relationships");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(parser.parseRelationship(child, usage)).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
    }

    public String createIndexForDependency() throws Exception {
        WebResource resource = Client.create().resource(INDEX_ENTRY_POINT_URI + "/node");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(parser.parseIndexDependency()).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
        if (response.getStatus() == 404 || response.getStatus() == 400) {
            throw new Exception(response.getEntity(String.class));
        }
        return response.getLocation().toString();

    }

    public void attacheIndexToDependency(String dependencyUri, String fullQualifier, String indexUri) throws Exception {
        WebResource resource = Client.create().resource(indexUri + "fullqualifier/" + fullQualifier);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(parser.uriToJson(dependencyUri)).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
        if (response.getStatus() == 404 || response.getStatus() == 400 || response.getStatus() == 405) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public String postNode(String jsonProperties) throws Exception {
        WebResource resource = Client.create().resource(NODE_ENTRY_POINT_URI);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(jsonProperties).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
        if (response.getStatus() == 400 || response.getStatus() == 404) {
            throw new Exception(response.getEntity(String.class));
        }
        return response.getLocation().toString();
    }


    public String getDependencyUri(String fullQualifier) throws IOException {
        WebResource resource = Client.create().resource(INDEX_ENTRY_POINT_URI + "/node/dependency/fullqualifier/" +
                fullQualifier);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        logRequest("GET", resource.getURI(), response);
        if (response.getStatus() == 404) {
            return null;
        } else if (response.getStatus() == 500) {
            System.out.println(response.getEntity(String.class));
            return null;
        }
        Map<String, Object> dependencyMap = parser.parseJsonToMap(response.getEntity(String.class));
        if (dependencyMap == null || dependencyMap.isEmpty()) {
            return null;
        } else {
            return (String) dependencyMap.get("self");
        }
    }

    public Map<String, String> getPropertiesNode(String nodeUri) throws IOException {
        WebResource resource = Client.create().resource(nodeUri + "/properties");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        logRequest("GET", resource.getURI(), response);
        return parser.parseJsonToMap(response.getEntity(String.class));
    }

    public String getDependencyNode(String dependencyUri, String traverseJson) {
        WebResource resource = Client.create().resource(dependencyUri + "/traverse/relationship");
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(traverseJson).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
        return response.getEntity(String.class);
    }

    public String executeBatchRequests(String jsonRequest) throws Exception {
        WebResource resource = Client.create().resource(BATCH_URI);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(jsonRequest).post(ClientResponse.class);
        logRequest("POST", resource.getURI(), response);
        if (response.getStatus() == 400 || response.getStatus() == 404) {
            throw new Exception(response.getEntity(String.class));
        }
        return response.getEntity(String.class);
    }


    public static void logRequest(String type, URI request, ClientResponse response) {
        logger.info(type + " to " + request + ", status code " + response.getStatus() + ", location header " +
                (response.getLocation() != null ? response.getLocation().toString() : " null"));
    }

    private String getNodeId(String str) {
        return str.split("/")[str.split("/").length - 1];
    }
}
