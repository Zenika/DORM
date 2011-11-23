package com.zenika.dorm.core.dao.neo4j;

import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import com.zenika.dorm.core.dao.query.DormBasicQuery;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Neo4jGetTask extends Neo4jAbstractTask {

    @Inject
    private DormBasicQuery query;

    @Override
    public DormMetadata execute() {
        Neo4jResponse<Neo4jMetadata> metadataResponse = getMetadata(query);
            return convertToDormMetadata(metadataResponse);
    }

}
