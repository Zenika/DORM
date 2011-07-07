package com.zenika.dorm.core.modelnew.impl;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated remplaced by Usage
 */
public class DormScope {

    public static final String DEFAULT_SCOPE = "default";

    private String name;

    public DormScope(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormScope scope = (DormScope) o;

        if (!name.equals(scope.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
