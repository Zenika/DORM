package com.zenika.dorm.core.model.graph.proposal1;

import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;

/**
 * Represents a dependency on an artifact + an usage
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface Dependency {

    public Usage getUsage();

    public void setUsage(Usage usage);

    public DormMetadata getMetadata();

    public DormFile getFile();

    public void setFile(DormFile file);

    public Boolean hasFile();

    /**
     * @deprecated soon :p
     */
    public Boolean isMainDependency();
}