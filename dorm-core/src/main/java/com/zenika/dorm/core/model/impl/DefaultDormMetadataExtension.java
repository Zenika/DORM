package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.DormMetadataExtension;

import java.util.Map;

/**
 * Default immutable dorm origin
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDormMetadataExtension implements DormMetadataExtension {

    public static final String NAME = "dorm";

    /**
     * Metadata names
     */
    public static final String METADATA_NAME = "name";

    private final String name;

    public DefaultDormMetadataExtension() {
        this.name = null;
    }

    public DefaultDormMetadataExtension(String name) {
        this.name = name;
    }

    @Override
    public String getQualifier() {
        return name;
    }

    @Override
    public String getExtensionName() {
        return NAME;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultDormMetadataExtension origin = (DefaultDormMetadataExtension) o;

        if (name != null ? !name.equals(origin.name) : origin.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public DormMetadataExtension createFromMap(Map<String, String> properties) {
        return new DefaultDormMetadataExtension(properties.get(METADATA_NAME));
    }
}
