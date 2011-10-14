package com.zenika.dorm.core.model;


/**
 * Extension point on the model to add specific metadatas
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormMetadata {

    protected final Long id;

    protected DormMetadata() {
        id = null;
    }

    public DormMetadata(Long id) {
        this.id = id;
    }

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getType();

    public final String getFunctionalId() {
        return getType() + ":" +
                getName() + ":" +
                getVersion();
    }

    public Long getId() {
        return id;
    }
}