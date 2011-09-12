package com.zenika.dorm.core.dao.neo4j.wrapper;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Neo4jWrapper {

    public void addMappingConfig(ObjectMapper mapper);
}
