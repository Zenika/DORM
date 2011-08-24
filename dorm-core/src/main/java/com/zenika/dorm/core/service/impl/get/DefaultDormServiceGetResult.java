package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.impl.DefaultDormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetResult extends DefaultDormServiceProcess implements DormServiceGetResult {

    private DependencyNode node;

    public DefaultDormServiceGetResult(String processName, DependencyNode node) {
        super(processName);
        this.node = node;
    }

    @Override
    public DependencyNode getNode() {
        return node;
    }
}
