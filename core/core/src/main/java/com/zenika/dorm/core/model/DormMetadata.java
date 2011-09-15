package com.zenika.dorm.core.model;


/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormMetadata {

    public String getIdentifier();

    public String getVersion();

    public String getExtensionName();
}