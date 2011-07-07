package com.zenika.dorm.core.modelnew.impl;

import com.zenika.dorm.core.modelnew.DormOrigin;

/**
 * Default dorm origin
 *
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultDormOrigin that = (DefaultDormOrigin) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
