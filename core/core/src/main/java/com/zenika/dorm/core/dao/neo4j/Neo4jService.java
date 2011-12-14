package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.exception.CoreException;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jService {

    protected static final String GREMLIN_URI = "ext/GremlinPlugin/graphdb/execute_script";
    protected static final String NODE_PATH = "node";
    protected static final String NODE_URI = DormDaoNeo4j.DATA_ENTRY_POINT_URI + "/node";
    protected static final String AUTO_INDEX_URI = "index/auto/node/{property}/{value}";

    public static final GenericType<List<Neo4jRelationship>> RESPONSE_LIST_RELATIONSHIP =
            new GenericType<List<Neo4jRelationship>>() {
            };
    public static final GenericType<List<Neo4jResponse<Neo4jMetadata>>> RESPONSE_LIST_METADATA =
            new GenericType<List<Neo4jResponse<Neo4jMetadata>>>() {
            };
    public static final GenericType<Neo4jResponse<Neo4jMetadata>> RESPONSE_METADATA =
            new GenericType<Neo4jResponse<Neo4jMetadata>>() {
            };
    public static final GenericType<List<Neo4jResponse<Neo4jLabel>>> RESPONSE_LIST_LABEL =
            new GenericType<List<Neo4jResponse<Neo4jLabel>>>() {};
    public static final GenericType<Neo4jResponse<Neo4jLabel>> RESPONSE_LABEL =
            new GenericType<Neo4jResponse<Neo4jLabel>>() {
            };

    private WebResource resource;

    @Inject
    public Neo4jService(Neo4jWebResourceWrapper wrapper) {
        this.resource = wrapper.get();
    }

    public <T> List<Neo4jResponse<T>> getNodesByGremlinScript(String gremlinScript, GenericType<List<Neo4jResponse<T>>> type) {
        return getDefaultResource(GREMLIN_URI)
                .entity(gremlinScript)
                .post(type);
    }

    public <T> Neo4jResponse<T> getNodeByGremlinScript(String gremlinScript, GenericType<List<Neo4jResponse<T>>> type) {
        List<Neo4jResponse<T>> neo4jResponseList = getNodesByGremlinScript(gremlinScript, type);
        return getFistElement(neo4jResponseList);
    }

    public <T> Neo4jResponse<T> getNode(Long id, GenericType<Neo4jResponse<T>> type) {
        return getDefaultResource(new StringBuilder(50).append(NODE_PATH).append("/").append(id).toString())
                .get(type);
    }

    public <T> Neo4jResponse<T> getNode(String nodeUri, GenericType<Neo4jResponse<T>> type) {
        URI uri;
        try {
            uri = new URI(nodeUri);
        } catch (URISyntaxException e) {
            throw new CoreException("Unable to create this uri: " + nodeUri, e);
        }
        return getDefaultResource(uri).get(type);
    }

    public <T> Neo4jResponse<T> getNodeByIndex(String key, String value, Neo4jIndex index, GenericType<List<Neo4jResponse<T>>> type) {
        List<Neo4jResponse<T>> neo4jResponseList = getDefaultResource(index.fillIndexTemplatePath(key, value))
                .get(type);
        return getFistElement(neo4jResponseList);
    }

    public Neo4jRelationship getRelationship(Neo4jResponse<Neo4jMetadata> metadataResponse, String labelType, Neo4jRelationship.Direction direction) {
        List<Neo4jRelationship> neo4jRelationships = getRelationships(metadataResponse, labelType, direction);
        return getFistElement(neo4jRelationships);
    }

    public List<Neo4jRelationship> getRelationships(Neo4jResponse neo4jResponse, String type, Neo4jRelationship.Direction direction) {
        URI uri;
        if (direction.equals(Neo4jRelationship.Direction.OUT)) {
            uri = neo4jResponse.getOutgoing_typed_relationships(type);
        } else if (direction.equals(Neo4jRelationship.Direction.IN)) {
            uri = neo4jResponse.getIncoming_typed_relationships(type);
        } else {
            uri = neo4jResponse.getAll_type_relationships(type);
        }
        return getDefaultResource(uri).get(RESPONSE_LIST_RELATIONSHIP);
    }

    public <T> Neo4jResponse<T> createNode(T node) {
        return getDefaultResource(NODE_PATH).entity(node).post(new GenericType<Neo4jResponse<T>>() {
        });
    }

    public void createIndex(String property, String value, String nodeUri, Neo4jIndex index) {
        getDefaultResource(index.getIndexLocation()).entity(index.addToIndex(property, value, nodeUri)).post();
    }

    public void createRelationship(Neo4jRelationship relationship) {
        URI createRelationship;
        try {
            createRelationship = new URI(relationship.getFrom());
        } catch (URISyntaxException e) {
            throw new CoreException("Uri syntax exception", e);
        }
        getDefaultResource(createRelationship).entity(relationship).post();
    }

    public void createRelationships(List<Neo4jRelationship> relationships) {
        for (Neo4jRelationship relationship : relationships) {
            createRelationship(relationship);
        }
    }

    public void deleteRelationships(List<Neo4jRelationship> relationships) {
        for (Neo4jRelationship relationship : relationships) {
            deleteRelationship(relationship);
        }
    }

    private void deleteRelationship(Neo4jRelationship relationship) {
        URI uri;
        try {
            uri = new URI(relationship.getSelf());
        } catch (URISyntaxException e) {
            throw new CoreException("Unable to create this uri: " + relationship.getSelf(), e);
        }
        getDefaultResource(uri).delete();

    }

    private <T> T getFistElement(List<T> list) {
        if (list.size() > 1) {
            throw new CoreException("More than one response was retrieved.");
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private WebResource.Builder getDefaultResource(String path) {
        return resource.path(path)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }

    private WebResource.Builder getDefaultResource(URI uri) {
        return resource.uri(uri)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);
    }
}
