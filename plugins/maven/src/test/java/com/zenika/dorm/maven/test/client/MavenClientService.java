package com.zenika.dorm.maven.test.client;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.exception.MavenException;
import org.sonatype.aether.RequestTrace;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.deployment.DeployRequest;
import org.sonatype.aether.deployment.DeployResult;
import org.sonatype.aether.deployment.DeploymentException;
import org.sonatype.aether.installation.InstallRequest;
import org.sonatype.aether.installation.InstallationException;

public class MavenClientService {

    private MavenClientConfig config;

    public MavenClientService(MavenClientConfig config) {
        this.config = config;
    }

    public void install(DormMetadata dormMetadata) {

        Artifact artifact = MavenClientHelper.fromDependencyToArtifact(dormMetadata);

        InstallRequest request = new InstallRequest();
        request.addArtifact(artifact);

        try {
            config.getSystem().install(config.getSession(), request);
        } catch (InstallationException e) {
            throw new MavenException("Install exception", e);
        }
    }

    public void deploy(DormMetadata dormMetadata) {
        Artifact artifact = MavenClientHelper.fromDependencyToArtifact(dormMetadata);

        DeployRequest request = new DeployRequest();
        request.addArtifact(artifact);
        request.setRepository(config.getRemoteRepository());

        DeployResult result;

        try {
            result = config.getSystem().deploy(config.getSession(), request);
        } catch (DeploymentException e) {
            throw new MavenException("Deploy exception", e);
        }

        RequestTrace trace = result.getRequest().getTrace();
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