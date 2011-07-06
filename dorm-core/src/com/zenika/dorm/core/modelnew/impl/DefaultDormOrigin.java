package com.zenika.dorm.core.modelnew.impl;

import com.zenika.dorm.core.modelnew.DormOrigin;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormOrigin implements DormOrigin {

    private static final String ORIGIN = "dorm";

    private String name;

    public DefaultDormOrigin(String name) {
        this.name = name;
    }

    @Override
    public String getQualifier() {
        return name;
    }

    @Override
    public String getOrigin() {
        return ORIGIN;
    }
}
