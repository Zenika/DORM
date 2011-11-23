package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jIndexProvider;
import com.zenika.dorm.core.dao.neo4j.provider.Neo4jWebResourceWrapper;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.service.spi.ExtensionFactoryServiceLoader;
import org.codehaus.jackson.map.ObjectMapper;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public abstract class Neo4jAbstractTask {

    private static final Logger LOG = LoggerFactory.getLogger(Neo4jAbstractTask.class.getName());

    @Inject
    protected Neo4jService neo4jService;
    @Inject
    protected Neo4jIndexProvider indexProvider;
    @Inject
    protected ExtensionFactoryServiceLoader serviceLoader;

    protected static void logRequest(String type, WebResource resource, String path) {
        LOG.info(type + " to " + resource.getURI() + "/" + path);
    }

    protected static void logRequest(String type, URI uri) {
        LOG.info(type + " to " + uri);
    }

    protected static void logRequest(String type, String uri) {
        LOG.info(type + " to " + uri);
    }

    protected Neo4jAbstractTask() {

    }

    public abstract Object execute();

    protected Neo4jResponse<Neo4jMetadata> getMetadata(DormBasicQuery query) {
            String gremlinScript = buildMetadataScript(query);
            LOG.info("Script: " + gremlinScript);
            return neo4jService.getNodeByGremlinScript(gremlinScript, Neo4jService.RESPONSE_LIST_METADATA);
    }

    protected Neo4jResponse<Neo4jLabel> getLabel(Neo4jLabel label, boolean withMetadata) {
        Neo4jResponse<Neo4jLabel> labelNeo4jResponse = neo4jService.getNodeByIndex("name", label.getLabel(), indexProvider.getLabelIndex(), Neo4jService.RESPONSE_LIST_LABEL);
        if (withMetadata) {
            return getLabelWithMetadata(labelNeo4jResponse);
        } else {
            return labelNeo4jResponse;
        }
    }

    private Neo4jResponse<Neo4jLabel> getLabelWithMetadata(Neo4jResponse<Neo4jLabel> labelNeo4jResponse) {
        if (labelNeo4jResponse == null) {
            return null;
        } else {
            Set<Neo4jResponse<Neo4jMetadata>> metadataResponseSet = new HashSet<Neo4jResponse<Neo4jMetadata>>();
            List<Neo4jRelationship> neo4jRelationships = neo4jService.getRelationships(labelNeo4jResponse, Neo4jRelationship.LABEL_TYPE, Neo4jRelationship.Direction.OUT);
            for (Neo4jRelationship neo4jRelationship : neo4jRelationships) {
                Neo4jResponse<Neo4jMetadata> neo4jResponse = neo4jService.getNode(neo4jRelationship.getEnd(), Neo4jService.RESPONSE_METADATA);
                metadataResponseSet.add(neo4jResponse);
            }
            labelNeo4jResponse.getData().setMetadataResponseSet(metadataResponseSet);
            return labelNeo4jResponse;
        }
    }

    protected DormMetadata convertToDormMetadata(Neo4jResponse<Neo4jMetadata> metadataResponse) {
        Neo4jMetadata metadata = metadataResponse.getData();
        return serviceLoader.getInstanceOf(metadata.getExtensionName())
                .fromMap(extractId(metadataResponse.getSelf()), metadata.getProperties());
    }

    private String buildMetadataScript(DormBasicQuery query) {
        return new StringBuilder(50)
                .append("{\"script\": \"g.V.filter{it.extensionName == \\\"")
                .append(query.getExtensionName())
                .append("\\\" && it.name == \\\"")
                .append(query.getName())
                .append("\\\" && it.version == \\\"")
                .append(query.getVersion())
                .append("\\\"}\"}")
                .toString();
    }

    protected Long extractId(String uri) {
        String[] split = uri.split("/");
        return Long.valueOf(split[split.length - 1]);
    }

    protected String generateNodeUri(Long id) {
        return neo4jService.NODE_PATH + id;
    }

    protected String generateCreateRelationshipUri(Long id) {
        return generateNodeUri(id) + "/relationships";
    }


}
