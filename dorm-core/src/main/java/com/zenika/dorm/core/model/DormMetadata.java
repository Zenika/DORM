package com.zenika.dorm.core.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated see package core.modelnew or core.model.graph
 */
@Embedded
public final class DormMetadata<T extends MetadataExtension> {

    public final static String ORIGIN = "Dorm";

    private String name;
    private String version;
    private String origin;

    @Embedded
    private T extension;

    public DormMetadata(String name, String version, String origin) {
        this.name = name;
        this.version = version;
        this.origin = origin;
    }

    public DormMetadata(String name, String version) {
        this(name, version, DormMetadata.ORIGIN);
    }

    public String getFullQualifier() {
        return name + ":" + version + ":" + origin;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormMetadata that = (DormMetadata) o;

        if (!name.equals(that.name)) return false;
        if (!origin.equals(that.origin)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + origin.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return getFullQualifier();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getExtension() {
        return extension;
    }

    public void setExtension(T extension) {
        this.extension = extension;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
