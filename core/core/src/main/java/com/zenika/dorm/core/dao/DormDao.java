package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

public interface DormDao {

    public DormMetadata getMetadataByQualifier(String qualifier);

    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage);

    public void saveMetadata(DormMetadata metadata);
}
