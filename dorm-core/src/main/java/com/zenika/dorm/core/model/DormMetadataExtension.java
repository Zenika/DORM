package com.zenika.dorm.core.model;

import java.util.Map;

/**
 * Extension point on the model to add specific metadatas
 * These metadatas are identified by the qualifier
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormMetadataExtension {

    /**
     * The internal qualifier of the origin which represents the unique composition of all origin's metadatas
     * Pattern : "attribute1:attribute2:etc..."
     *
     * @return the internal qualifier
     * @see com.zenika.dorm.core.model.DormMetadata#getQualifier()
     */
    public String getQualifier();

    /**
     * The unique string representation of the origin
     *
     * @return the origin identifier
     */
    public String getExtensionName();

    /**
     * Create new instance with a map of new attributes
     *
     * @return new instance mapped with the new attributes
     */
    public DormMetadataExtension createFromMap(Map<String, String> properties);
}