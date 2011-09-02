package com.zenika.dorm.core.service.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.impl.DefaultDormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServiceStoreResult extends DefaultDormServiceProcess {

    private DependencyNode savedNode;

    public DormServiceStoreResult(String processName) {
        super(processName);
    }

    public DependencyNode getSavedNode() {
        return savedNode;
    }

    public void setSavedNode(DependencyNode savedNode) {
        this.savedNode = savedNode;
    }
}