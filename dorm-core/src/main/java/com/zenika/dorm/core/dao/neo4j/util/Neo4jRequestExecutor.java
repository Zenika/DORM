package com.zenika.dorm.core.dao.neo4j.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Node;
import com.zenika.dorm.core.dao.neo4j.*;
import com.zenika.dorm.core.model.DormMetadataExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jRequestExecutor implements RequestExecutor {

    private static Logger logger = LoggerFactory.getLogger(Neo4jRequestExecutor.class.getName());

    public static final String DATA_ENTRY_POINT_URI = "http://localhost:7474/db/data";
    public static final String NODE_PATH = "node";

    private WebResource resource;

    public Neo4jRequestExecutor() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().addAll(getClasses());
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        Client client = Client.create(config);
        resource = client.resource(DATA_ENTRY_POINT_URI);
    }

    @Override
    public <T extends Neo4jNode> void post(T node) {
        Neo4jResponse<T> response = resource.path(NODE_PATH).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .entity(node).post(new GenericType<Neo4jResponse<T>>() {
                });
        node.setResponse(response);
        logRequest("POST", resource, NODE_PATH);
    }

    @Override
    public Neo4jResponse<Map<String, String>> postExtension(Map<String, String> properties) {
        Neo4jResponse<Map<String, String>> response = resource.path(NODE_PATH).accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).entity(properties).post(new GenericType<Neo4jResponse<Map<String, String>>>() {
                });
        logRequest("POST", resource, NODE_PATH);
        return response;
    }

    @Override
    public void post(Neo4jRelationship relationship) throws URISyntaxException {
        resource.uri(new URI(relationship.getFrom())).accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(relationship);
        logRequest("POST", relationship.getFrom());
    }

    @Override
    public Neo4jIndex post(Neo4jIndex index) {
        logger.trace(Neo4jIndex.INDEX_PATH);

        try {
            index = resource.path(Neo4jIndex.INDEX_PATH).accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON).post(Neo4jIndex.class, index);
        } catch (Exception e) {
            logger.info("Cannot establish connection to the neo4j server", e);
        }

        logRequest("POST", resource, Neo4jIndex.INDEX_PATH);
        return index;
    }

    @Override
    public void post(Neo4jNode node, URI indexUri) {
        resource.uri(indexUri).type(MediaType.APPLICATION_JSON).post("\"" + node.getResponse().getSelf() + "\"");
        logRequest("POST", indexUri.toString());
    }

    @Override
    public List<Neo4jRelationship> post(URI uri, Neo4jTraverse traverse) {
        logger.info("Traverse : " + traverse);
        List<Neo4jRelationship> relationships = resource.uri(uri).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(traverse)
                .post(new GenericType<List<Neo4jRelationship>>() {
                });
        logRequest("POST", uri);
        return relationships;
    }

    @Override
    public <T> T get(URI uri, Class<T> type) {
        T object = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(type);
        logRequest("GET", uri);
        return object;
    }

    @Override
    public <T> T get(URI uri, Type type) {
        T object = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(new GenericType<T>(type));
        logRequest("GET", uri);
        return object;
    }

    @Override
    public <T extends Neo4jNode> T getNode(URI uri, Type type) throws ClientHandlerException, UniformInterfaceException {
        Neo4jResponse<T> response = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(new GenericType<Neo4jResponse<T>>(type));
        T node = response.getData();
        node.setResponse(response);
        node.setProperties();
        logRequest("GET", uri);
        return node;
    }

    @Override
    public Neo4jMetadataExtension getExtension(URI uri, DormMetadataExtension dormExtension) throws ClientHandlerException, UniformInterfaceException {
        Neo4jResponse<Map<String, String>> response = resource.uri(uri).accept(MediaType.APPLICATION_JSON).get(new GenericType<Neo4jResponse<Map<String, String>>>() {});
        Neo4jMetadataExtension extension = new Neo4jMetadataExtension();
        extension.setExtension(dormExtension.createFromMap(response.getData()));
        extension.setResponse(response);
        logRequest("GET", uri);
        return extension;
    }

    @Override
    public <T extends Neo4jNode> T getNode(URI uri) {
        Neo4jResponse<T> response = resource
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<Neo4jResponse<T>>() {
                });
        T node = response.getData();
        node.setResponse(response);
        node.setProperties();
        logRequest("GET", uri);
        return node;
    }

    public String test() {
        return "1";
    }

    public static void logRequest(String type, WebResource resource, String path) {
        logger.info(type + " to " + resource.getURI() + "/" + path);
    }

    public static void logRequest(String type, URI uri) {
        logger.info(type + " to " + uri);
    }

    public static void logRequest(String type, String uri) {
        logger.info(type + " to " + uri);
    }

    private static Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ObjectMapperProvider.class);
        classes.add(Neo4jDependency.class);
        classes.add(Neo4jMetadata.class);
//        classes.add(Neo4jMetadataExtension.class);
        classes.add(Neo4jResponse.class);
        classes.add(Neo4jIndex.class);
        return classes;
    }
}
