package com.zenika.dorm.core.model;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormMetadata {

    /**
     * Represents the unique qualifier of an dependency
     *
     * @return the unique qualifier
     */
    public String getQualifier();

    public String getVersion();

    public DormMetadataExtension getExtension();
}
