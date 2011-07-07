package com.zenika.dorm.core.modelnew.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated since dorm will represent artifacts in more generic way, like graphs and nodes
 */
final public class DormArtifact {

    private String qualifier;
    private String version;

    private DormModule module;
    private DormFile file;
    private List<DormScope> scopes = new ArrayList<DormScope>();

    public DormArtifact(String qualifier, String version) {
        this.qualifier = qualifier;
        this.version = version;
    }

    public void addScope(DormScope scope) {
        scopes.add(scope);
    }

    public String getFullQualifier() {
        return qualifier + ":" + version + ":scope=" + scopes;
    }

    @Override
    public String toString() {
        return getFullQualifier();
    }

    public List<DormScope> getScopes() {
        return scopes;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DormModule getModule() {
        return module;
    }

    public void setModule(DormModule module) {
        this.module = module;
    }

    public DormFile getFile() {
        return file;
    }

    public void setFile(DormFile file) {
        this.file = file;
    }
}
