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

    /**
     * The type of the medatada, commonly the extension of the file associated to the metadata
     *
     * @return the type
     */
    public String getType();

    public DormMetadataExtension getExtension();
}
