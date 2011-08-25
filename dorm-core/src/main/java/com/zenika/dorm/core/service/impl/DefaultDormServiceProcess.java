package com.zenika.dorm.core.service.impl;

import com.zenika.dorm.core.service.DormServiceProcess;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class DefaultDormServiceProcess implements DormServiceProcess {

    protected String processName;

    protected DefaultDormServiceProcess(String processName) {
        this.processName = processName;
    }

    @Override
    public String getProcessName() {
        return processName;
    }
}
