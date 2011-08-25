package com.zenika.dorm.core.service.impl.put;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.impl.DefaultDormServiceProcess;
import com.zenika.dorm.core.service.put.DormServicePutResult;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServicePutResult extends DefaultDormServiceProcess implements DormServicePutResult {

    private DependencyNode savedNode;

    public DefaultDormServicePutResult(String processName) {
        super(processName);
    }

    @Override
    public DependencyNode getSavedNode() {
        return savedNode;
    }

    public void setSavedNode(DependencyNode savedNode) {
        this.savedNode = savedNode;
    }
}
