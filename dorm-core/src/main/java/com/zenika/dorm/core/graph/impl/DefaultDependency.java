package com.zenika.dorm.core.graph.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDependency implements Dependency {

    private Usage usage;
    private DormMetadata metadata;
    private DormFile file;

    public static DefaultDependency create(DormMetadata metadata) {
        return new DefaultDependency(metadata, Usage.create(), null);
    }

    public static DefaultDependency create(DormMetadata metadata, DormFile file) {
        return new DefaultDependency(metadata, Usage.create(), file);
    }

    public static DefaultDependency create(DormMetadata metadata, Usage usage) {
        return new DefaultDependency(metadata, usage, null);
    }

    public static DefaultDependency create(DormMetadata metadata, Usage usage, DormFile file) {
        return new DefaultDependency(metadata, usage, file);
    }

    /**
     * @see DefaultDependency#create(com.zenika.dorm.core.model.DormMetadata)
     * @deprecated Use factory methods
     */
    public DefaultDependency(DormMetadata metadata) {
        this(metadata, Usage.create());
    }

    /**
     * @see DefaultDependency#create(com.zenika.dorm.core.model.DormMetadata, com.zenika.dorm.core.model.DormFile)
     * @deprecated Use factory methods
     */
    public DefaultDependency(DormMetadata metadata, DormFile file) {
        this(metadata, Usage.create(), file);
    }

    /**
     * @see DefaultDependency#create(com.zenika.dorm.core.model.DormMetadata, Usage)
     * @deprecated Use factory methods
     */
    public DefaultDependency(DormMetadata metadata, Usage usage) {
        this(metadata, usage, null);
    }

    /**
     * @see DefaultDependency#create(com.zenika.dorm.core.model.DormMetadata, Usage, com.zenika.dorm.core.model.DormFile)
     * @deprecated Use factory methods
     */
    public DefaultDependency(DormMetadata metadata, Usage usage, DormFile file) {

        if (null == metadata || null == usage) {
            throw new CoreException("Metadata and usage are required.");
        }

        this.metadata = metadata;
        this.usage = usage;
        this.file = file;
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
    public DormFile getFile() {
        return file;
    }

    @Override
    public Boolean hasFile() {
        return null != file;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public void setMetadata(DormMetadata metadata) {
        this.metadata = metadata;
    }


    public void setFile(DormFile file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDependency)) return false;

        DefaultDependency that = (DefaultDependency) o;

        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        if (usage != null ? !usage.equals(that.usage) : that.usage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usage != null ? usage.hashCode() : 0;
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}