package com.zenika.dorm.core.modelnew.impl;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 * @deprecated since dorm will represent artifacts in more generic way, like graphs and nodes
 */
final public class DormModule {

    private String qualifier;
    private String version;
    private DormOrigin origin;

    private List<DormArtifact> artifacts = new ArrayList<DormArtifact>();
    private Map<String, DormScope> scopes = new HashMap<String, DormScope>();
    private Map<DormScope, List<DormArtifact>> artifactsByScope = new HashMap<DormScope,
            List<DormArtifact>>();

    public DormModule(String version, DormOrigin origin) {
        this.version = version;
        this.origin = origin;
    }

    public DormScope getScope(String identifier) {
        DormScope scope = null;

        try {
            scope = scopes.get(identifier);
        } catch (Exception e) {
            throw new CoreException("invalid scope");
        }

        if (null == scope) {
            scope = new DormScope(identifier);
            scopes.put(identifier, scope);
        }

        return scope;
    }

    public void addArtifact(DormArtifact artifact, String scope) {
        addArtifact(artifact, getScope(scope));
    }

    public void addArtifact(DormArtifact artifact, DormScope scope) {

        artifact.setModule(this);
        artifact.addScope(scope);

        if (!artifacts.contains(artifact)) {
            artifacts.add(artifact);
        }

        List<DormArtifact> scopeArtifacts = artifactsByScope.get(scope);
        if (null == scopeArtifacts) {
            scopeArtifacts = new ArrayList<DormArtifact>();
            artifactsByScope.put(scope, scopeArtifacts);
        }

        scopeArtifacts.add(artifact);
    }

    public void addArtifact(DormArtifact artifact) {
        addArtifact(artifact, DormScope.DEFAULT_SCOPE);
    }

    public String getQualifier() {

        if (null == qualifier) {
            qualifier = origin.getQualifier();
        }

        return qualifier;
    }

    public String getFullQualifier() {
        return getQualifier() + ":" + version + ":" + origin.getOrigin();
    }

    @Override
    public String toString() {
        return getFullQualifier();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DormOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(DormOrigin origin) {
        this.origin = origin;
    }

    public List<DormArtifact> getArtifacts() {
        return artifacts;
    }

    public List<DormArtifact> getArtifactsByScope(String scope) {
        return artifactsByScope.get(getScope(scope));
    }

    public List<DormArtifact> getArtifactsByScope(DormScope scope) {
        return artifactsByScope.get(scope);
    }
}
