package com.zenika.dorm.maven.client;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.artifact.ArtifactType;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.DefaultArtifactType;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientHelper {

    public static Artifact fromDependencyToArtifact(Dependency dependency) {

        MavenMetadata extension;

        try {
            extension = (MavenMetadata) dependency.getMetadata().getExtension();
        } catch (ClassCastException e) {
            throw new MavenException("Extension is not maven");
        }

        ArtifactType type = new DefaultArtifactType(extension.getExtension());
        Artifact artifact = new DefaultArtifact(extension.getGroupId(), extension.getArtifactId(),
                "toto", extension.getExtension(), extension.getVersion(), type);

        if (dependency.hasResource()) {
            return artifact.setFile(dependency.getResource().getFile());
        }

        return artifact;
    }
}