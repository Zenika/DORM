package com.zenika.dorm.core.dao;


import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;

import java.util.List;
import java.util.Map;

public interface DormDao {

    /**
     * Retrieve a DormMetadata according the qualifier.
     *
     * @param qualifier The qualifier. Ex : "maven:commons-io:commons-io:1.0.0:::jar:1.0.0"
     * @return the DormMetadata
     */
    public DormMetadata getMetadataByFunctionalId(String qualifier);

    public List<DormMetadata> getMetadataByExtension(String extensionName, Map<String, String> extensionClauses, Usage usage);

    /**
     * Save the DormMetadata
     *
     * @param metadata The metadata
     */
    public void saveMetadata(DormMetadata metadata);
}
