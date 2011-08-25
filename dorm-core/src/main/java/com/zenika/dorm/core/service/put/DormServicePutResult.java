package com.zenika.dorm.core.service.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.DormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServicePutResult extends DormServiceProcess {

    public DependencyNode getSavedNode();
}
