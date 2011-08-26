package com.zenika.dorm.core.service.impl;

import com.zenika.dorm.core.service.DormServiceProcess;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("processName", processName)
                .appendSuper(super.toString())
                .toString();
    }
}
