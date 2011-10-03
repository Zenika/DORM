package com.zenika.dorm.core.model;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormMetadata {

    protected final Long id;

    public DormMetadata(Long id) {
        this.id = id;
    }

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getExtensionName();

    public final String getFunctionalId() {
        return getExtensionName() + ":" +
                getName() + ":" +
                getVersion();
    }

    public Long getId() {
        return id;
    }
}