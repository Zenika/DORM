package com.zenika.dorm.maven.client;

import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientHelper {

    public static Artifact fromDependencyToArtifact(Dependency dependency) {

        MavenMetadataExtension extension;

        try {
            extension = (MavenMetadataExtension) dependency.getMetadata().getExtension();
        } catch (ClassCastException e) {
            throw new MavenException("Extension is not maven");
        }

        Artifact artifact = new DefaultArtifact(extension.getGroupId(), extension.getArtifactId(),
                extension.getClassifier(), dependency.getMetadata().getType(), extension.getVersion());

        if (dependency.hasResource()) {
            return artifact.setFile(dependency.getResource().getFile());
        }

        return artifact;
    }
}
