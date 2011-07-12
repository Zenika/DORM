package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDependency implements Dependency {

    private Usage usage;
    private DormMetadata metadata;
    private DormFile file;

    public DefaultDependency(DormMetadata metadata) {
        this(metadata, new Usage(Usage.DEFAULT));
    }

    public DefaultDependency(DormMetadata metadata, DormFile file) {
        this(metadata, new Usage(Usage.DEFAULT), file);
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

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    @Override
    public DormMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DormMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public DormFile getFile() {
        return file;
    }

    public void setFile(DormFile file) {
        this.file = file;
    }

    @Override
    public Boolean hasFile() {
        return null != file;
    }
}
