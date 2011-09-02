package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.service.get.DormServiceGetValues;

import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormDao {

    public Boolean push(Dependency node);

    public Boolean push(DependencyNode node);

    public List<DependencyNode> get(DormServiceGetValues values, boolean withDependencies);

    public DependencyNode getOne(DormServiceGetValues values, boolean withDependencies);

    public DormMetadata getByQualifier(String qualifier, Usage usage);

    public List<DormMetadata> getByMetadataExtension(String extensionName, Map<String,
            String> extensionClauses, Usage usage);
}
