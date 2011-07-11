package com.zenika.dorm.core.model.graph.proposal1.impl;

/**
 * Similar to Configuration in ivy or Scope in maven
 * Maybe find better name for that ?
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Usage {

    public static final String DEFAULT = "default";

    private String name;

    public Usage(String name) {
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

        Usage usage = (Usage) o;

        if (!name.equals(usage.name)) return false;

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
