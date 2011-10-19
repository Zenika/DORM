package com.zenika.dorm.maven.client;

import com.zenika.dorm.maven.exception.MavenException;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.artifact.ArtifactType;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.DefaultArtifactType;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientHelper {

    public static Artifact fromDependencyToArtifact(Dependency dependency) {

        MavenMetadata metadata;

        try {
            metadata = (MavenMetadata) dependency.getMetadata();
        } catch (ClassCastException e) {
            throw new MavenException("Extension is not maven");
        }

        ArtifactType type = new DefaultArtifactType(metadata.getBuildInfo().getExtension());
        Artifact artifact = new DefaultArtifact(metadata.getGroupId(), metadata.getArtifactId(),
                "toto", metadata.getBuildInfo().getExtension(), metadata.getVersion(), type);

        if (dependency.hasResource()) {
            return artifact.setFile(dependency.getResource().getFile());
        }

        return artifact;
    }
}
