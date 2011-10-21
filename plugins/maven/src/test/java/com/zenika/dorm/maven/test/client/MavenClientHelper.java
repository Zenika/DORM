package com.zenika.dorm.maven.test.client;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.artifact.ArtifactType;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.DefaultArtifactType;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientHelper {

    public static Artifact fromDependencyToArtifact(DormMetadata dormMetadata) {

        MavenPluginMetadata mavenPluginMetadata;

        try {
            mavenPluginMetadata = (MavenPluginMetadata) dormMetadata.getPlugin(MavenPluginMetadata.MAVEN_PLUGIN);
        } catch (ClassCastException e) {
            throw new MavenException("Extension is not maven");
        }

        ArtifactType type = new DefaultArtifactType(mavenPluginMetadata.getBuildInfo().getExtension());
        Artifact artifact = new DefaultArtifact(mavenPluginMetadata.getGroupId(), mavenPluginMetadata.getArtifactId(),
                "toto", mavenPluginMetadata.getBuildInfo().getExtension(), mavenPluginMetadata.getVersion(), type);

        if (dormMetadata.hasDerivedObject()) {
            return artifact.setFile(new File(dormMetadata.getDerivedObject().getLocation()));
        }

        return artifact;
    }
}