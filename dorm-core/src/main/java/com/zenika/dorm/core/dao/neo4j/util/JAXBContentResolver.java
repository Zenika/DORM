package com.zenika.dorm.core.dao.neo4j.util;

import com.sun.jersey.api.json.JSONJAXBContext;
import com.zenika.dorm.core.dao.neo4j.Neo4jDependency;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadataExtension;
import com.zenika.dorm.core.dao.neo4j.Neo4jResponse;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
public class JAXBContentResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;
    private final Set<Class> types;
    private final Class[] cTypes = {Neo4jDependency.class, Neo4jMetadata.class, Neo4jMetadataExtension.class, Neo4jResponse.class};

    public JAXBContentResolver() throws JAXBException {
        this.types = new HashSet<Class>(Arrays.asList(cTypes));
        this.context = new JSONJAXBContext(cTypes);
    }

    public JAXBContext getContext(Class<?> type) {
        return (types.contains(type)) ? context : null;
    }
}
