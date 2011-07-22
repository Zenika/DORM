package com.zenika.dorm.core.dao.neo4j.util;

import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormMetadataExtension;

import java.io.IOException;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface DormDaoParser {

public String parseOriginProperty(DormMetadataExtension origin);

    public Map<String, String> parseOriginPropertyToMap(DormMetadataExtension origin);

    public String parseMetaDataProperty(DormMetadata metadata);

    public Map<String, String> parseMetaDataPropertyToMap(DormMetadata metadata);

    public String parseRelationship(String child, Usage usage);

    public Map<String, String> parseRelationshipToMap(String child, Usage usage);

    public String parseIndexDependency();

    public Map<String, Object> parseIndexDependencyToMap();

    public String parseTraverse(Usage usage);

    public Map<String, DependencyNode> parseTraverseToDependency(DormMetadata dormMetadata, Usage usage, String traverse);

    public String parseDependencyToJson(Dependency dependency);



}
