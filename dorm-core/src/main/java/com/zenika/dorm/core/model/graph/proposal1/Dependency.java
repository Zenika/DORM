package com.zenika.dorm.core.model.graph.proposal1;

import com.zenika.dorm.core.model.graph.proposal1.impl.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;

/**
 * Represents a dependency on an artifact + an usage
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class Dependency {

    protected Usage usage;
    protected DormMetadata dormMetadata;

    public Dependency(DormMetadata dormMetadata, Usage usage) {
        this.dormMetadata = dormMetadata;
        this.usage = usage;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public DormMetadata getDormMetadata() {
        return dormMetadata;
    }

    public void setDormMetadata(DormMetadata dormMetadata) {
        this.dormMetadata = dormMetadata;
    }
}
