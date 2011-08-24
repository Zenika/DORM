package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.DormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetResult extends DormServiceProcess {

    public DependencyNode getNode();
}
