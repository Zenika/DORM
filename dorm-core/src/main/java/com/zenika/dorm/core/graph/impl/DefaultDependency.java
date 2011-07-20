package com.zenika.dorm.core.graph.impl;

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

    public DefaultDependency(DormMetadata metadata) {
        this(metadata, Usage.create());
    }

    public DefaultDependency(DormMetadata metadata, DormFile file) {
        this(metadata, Usage.create(), file);
    }

    public DefaultDependency(DormMetadata metadata, Usage usage) {
        this(metadata, usage, null);
    }

    public DefaultDependency(DormMetadata metadata, Usage usage, DormFile file) {
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
}