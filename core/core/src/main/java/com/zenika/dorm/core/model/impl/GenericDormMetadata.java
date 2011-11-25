package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * Generic implementation of DormMetadata
 * Represents a dorm metadata linked to no plugin/extension
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class GenericDormMetadata extends DormMetadata {

    public static final String EXTENSION_NAME = "dorm";

    public GenericDormMetadata(Long id) {
        super(id);
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType() {
        return EXTENSION_NAME;
    }
}
