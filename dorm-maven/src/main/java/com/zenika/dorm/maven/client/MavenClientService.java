package com.zenika.dorm.maven.client;

import com.zenika.dorm.maven.exception.MavenException;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.installation.InstallRequest;
import org.sonatype.aether.installation.InstallationException;

public class MavenClientService {

    private MavenClientConfig config;

    public MavenClientService(MavenClientConfig config) {
        this.config = config;
    }

    public void installArtifact(com.zenika.dorm.core.model.Dependency dependency) {

        Artifact artifact = MavenClientHelper.fromDependencyToArtifact(dependency);

        InstallRequest request = new InstallRequest();
        request.addArtifact(artifact);

        try {
            config.getSystem().install(config.getSession(), request);
        } catch (InstallationException e) {
            throw new MavenException("Install exception");
        }
    }

//    public DormArtifact<DormMavenMetadata> getFullArtifact(DormMavenMetadata metadata) {
//
//        // maven artifact
//        Artifact artifact = new DefaultArtifact(metadata.getArtifactName());
//
//        // create request
//        CollectRequest collectRequest = new CollectRequest();
//        collectRequest.setRoot(new Dependency(artifact, JavaScopes.COMPILE));
//        collectRequest.addRepository(repository);
//
//        // create dependency visitor
//        DormDependencyVisitor dependencyVisitor = new DormDependencyVisitor(system, session);
//
//        try {
//            // get results from the request
//            CollectResult collectResult = system.collectDependencies(session, collectRequest);
//
//            // and attach the visitor
//            collectResult.getRoot().accept(dependencyVisitor);
//
//        } catch (DependencyCollectionException e) {
//            throw new MavenException("Collect exception");
//        }
//
//        // return the root element
//        return dependencyVisitor.getRoot();
//    }
//
//    public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata metadata) {
//
//        // maven artifact
//        Artifact artifact = getArtifactFromMetadata(metadata, null);
//
//        ArtifactRequest request = new ArtifactRequest();
//        request.setArtifact(artifact);
//        request.addRepository(repository);
//
//        try {
//            ArtifactResult result = system.resolveArtifact(session, request);
//            artifact = result.getArtifact();
//        } catch (ArtifactResolutionException e) {
//            throw new MavenException("Resolve exception");
//        }
//
//        DormArtifact<DormMavenMetadata> dormArtifact = MavenHelper.createDormArtifact(artifact);
//        return dormArtifact;
//    }
}
