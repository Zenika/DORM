package com.zenika.dorm.core.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity
public final class DormArtifact<T extends MetadataExtension> {

    @Id
    private ObjectId id;

    @Embedded
    private DormMetadata<T> metadata;

    @Embedded
    private DormFile file;

    @Transient
    private List<DormArtifact<T>> dependencies = new ArrayList<DormArtifact<T>>();

    public DormArtifact(DormMetadata<T> metadata) {

        if (null == metadata) {
            throw new IllegalArgumentException("Metadata is null");
        }

        this.metadata = metadata;
    }

    public DormArtifact(DormMetadata<T> metadata, DormFile file) {
        this(metadata);
        this.file = file;
    }

    public DormArtifact(DormMetadata<T> metadata, DormFile file,
                        List<DormArtifact<T>> dependencies) {

        this(metadata, file);
        this.dependencies = dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DormArtifact that = (DormArtifact) o;

        if (!metadata.equals(that.metadata)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = metadata.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return metadata.getFullQualifier();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public DormMetadata<T> getMetadata() {
        return metadata;
    }

    public void setMetadata(DormMetadata<T> metadata) {
        this.metadata = metadata;
    }

    public DormFile getFile() {
        return file;
    }

    public void setFile(DormFile file) {
        this.file = file;
    }

    public List<DormArtifact<T>> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DormArtifact<T>> dependencies) {
        this.dependencies = dependencies;
    }
}
