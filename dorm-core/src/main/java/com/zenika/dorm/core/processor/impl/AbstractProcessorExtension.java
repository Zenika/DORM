package com.zenika.dorm.core.processor.impl;

import com.google.inject.Inject;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.core.processor.ProcessorHelper;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractProcessorExtension implements ProcessorExtension {

    @Inject
    private ProcessorHelper helper;

    protected ProcessorHelper getHelper() {
        return helper;
    }
}
