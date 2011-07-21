package com.zenika.dorm.core.model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormMetadata {

    public String getQualifier();

    public String getVersion();

    public DormMetadataExtension getExtension();

    /**
     * Represents the full and unique qualifier of an dependency : qualifier + version + origin
     *
     * @return the full and unique qualifier
     */
    public String getFullQualifier();
}
