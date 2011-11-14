package com.zenika.dorm.importer.test;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public interface MavenRepositoryGeneratorListener {

    public void artifactGenerated(MavenRepositoryArtifact mavenRepositoryArtifact);
}
