package com.zenika.dorm.core.model.impl;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Immutable dorm dependency
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DefaultDependency implements Dependency {

    private final Usage usage;
    private final DormMetadata metadata;
    private final DormResource resource;

    public static DefaultDependency create(DormMetadata metadata) {
        return new DefaultDependency(metadata, Usage.create(), null);
    }

    public static DefaultDependency create(DormMetadata metadata, DormResource resource) {
        return new DefaultDependency(metadata, Usage.create(), resource);
    }

    public static DefaultDependency create(DormMetadata metadata, Usage usage) {
        return new DefaultDependency(metadata, usage, null);
    }

    public static DefaultDependency create(DormMetadata metadata, Usage usage, DormResource resource) {
        return new DefaultDependency(metadata, usage, resource);
    }

    private DefaultDependency(DormMetadata metadata, Usage usage, DormResource resource) {
        this.metadata = checkNotNull(metadata);
        this.usage = checkNotNull(usage);
        this.resource = resource;
    }

    @Override
    public Usage getUsage() {
        return usage;
    }

    @Override
    public DormMetadata getMetadata() {
        return metadata;
    }

    @Override
    public DormResource getResource() {
        return resource;
    }

    @Override
    public Boolean hasResource() {
        return null != resource;
    }

    @Override
    public String toString() {
        String fileAsString = (resource == null) ? "null" : resource.toString();
        return "Dependency { " +
                "Usage = " + usage + "; " +
                "Metadata = " + metadata + "; " +
                "File = " + fileAsString + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultDependency that = (DefaultDependency) o;

        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usage != null ? usage.hashCode() : 0;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        return result;
    }
}