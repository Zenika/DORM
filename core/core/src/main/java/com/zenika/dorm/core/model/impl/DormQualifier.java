package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormQualifier {

    private final String qualifier;

    public DormQualifier(DormMetadata metadata) {
        qualifier = metadata.getExtensionName() + ":" +
                metadata.getIdentifier() + ":" +
                metadata.getVersion();
    }

    public String getQualifier() {
        return qualifier;
    }
}