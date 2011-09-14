package com.zenika.dorm.core.model;


/**
 * Extension point on the model to add specific metadatas
 * These metadatas are identified by the qualifier
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormMetadata {

    /**
     * The internal qualifier of the origin which represents the unique composition of all origin's metadatas
     * Pattern : "attribute1:attribute2:etc..."
     *
     * @return the internal qualifier
     * @see com.zenika.dorm.core.model.DormMetadata#getQualifier()
     */
    public String getQualifier();

    public String getVersion();

    /**
     * The unique string representation of the origin
     *
     * @return the origin identifier
     */
    public String getExtensionName();


}