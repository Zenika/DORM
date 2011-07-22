package com.zenika.dorm.core.dao.neo4j.util;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.zenika.dorm.core.dao.neo4j.Neo4jDependency;
import com.zenika.dorm.core.dao.neo4j.Neo4jMetadata;
import com.zenika.dorm.core.dao.neo4j.Neo4jOrigin;
import com.zenika.dorm.core.dao.neo4j.Neo4jUsage;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@Provider
public class JAXBContentResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;

    private final Class[] cTypes = {Neo4jMetadata.class};

    public JAXBContentResolver() throws Exception {
        this.context = new JSONJAXBContext(cTypes);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        for (Class type : cTypes) {
            if (type == objectType) {
                return context;
            }
        }
        return null;
    }
}
