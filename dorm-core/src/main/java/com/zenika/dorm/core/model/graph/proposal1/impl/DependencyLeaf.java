package com.zenika.dorm.core.model.graph.proposal1.impl;

import com.zenika.dorm.core.model.graph.proposal1.Dependency;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyLeaf extends Dependency {

    private DormFile file;

    public DependencyLeaf(DormMetadata dormMetadata, DormFile file, Usage usage) {
        super(dormMetadata, usage);
        this.file = file;
    }

    public DormFile getFile() {
        return file;
    }

    public void setFile(DormFile file) {
        this.file = file;
    }
}
