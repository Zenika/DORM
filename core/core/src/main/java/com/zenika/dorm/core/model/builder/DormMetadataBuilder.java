package com.zenika.dorm.core.model.builder;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DormMetadataBuilder<T extends DormMetadataBuilder> {

    protected Long id;

    protected DormMetadataBuilder() {
    }

    protected abstract T self();

    protected DormMetadataBuilder(DormMetadata metadata) {
        id(metadata.getId());
    }

    public T id(Long id) {
        this.id = id;
        return self();
    }
}
