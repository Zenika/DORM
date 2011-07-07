package com.zenika.dorm.core.model.graph.proposal1;

/**
 * Represents a dependency on an artifact + an usage
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class Dependency {

    private Usage usage;
    private Artifact artifact;

    public Dependency(Artifact artifact, Usage usage) {
        this.artifact = artifact;
        this.usage = usage;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        if (!artifact.equals(that.artifact)) return false;
        if (!usage.equals(that.usage)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usage.hashCode();
        result = 31 * result + artifact.hashCode();
        return result;
    }
}
