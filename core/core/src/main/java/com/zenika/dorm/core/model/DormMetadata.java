package com.zenika.dorm.core.model;


/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormMetadata {

    public abstract String getIdentifier();

    public abstract String getVersion();

    public abstract String getExtensionName();

    public final String getQualifier() {
        return getExtensionName() + ":" +
                getIdentifier() + ":" +
                getVersion();
    }
}