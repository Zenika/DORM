package com.zenika.dorm.core.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormMetadataLabel<T extends DormMetadata> {

    private final String label;
    private final Set<T> metadatas = new HashSet<T>();

    public DormMetadataLabel(String label) {
        this.label = label;
    }

    public void addMetadata(T metadata) {
        metadatas.add(metadata);
    }

    public Set<T> getMetadatas() {
        return metadatas;
    }

    public String getLabel() {
        return label;
    }
}
