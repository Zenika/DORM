package com.zenika.dorm.maven.importer.core;

import java.io.File;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.CollectResult;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.installation.InstallRequest;
import org.sonatype.aether.installation.InstallationException;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.JavaScopes;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.helper.MavenHelper;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public class MavenImporterService {

	private RepositorySystem system;
	private RepositorySystemSession session;
	private RemoteRepository repository;

	public MavenImporterService() {
		system = MavenRepositoryFactory.createRepositorySystem();
		session = MavenRepositoryFactory.createRepositorySystemSession(system);
		repository = MavenRepositoryFactory.createRemoteRepository();
	}

	public MavenImporterService(RepositorySystem system, RepositorySystemSession session) {
		this.system = system;
		this.session = session;
	}

	public MavenImporterService(RepositorySystem system, RepositorySystemSession session, RemoteRepository repository) {
		this(system, session);
		this.repository = repository;
	}

	public void installArtifact(DormMavenMetadata metadata, File file) {

		Artifact artifact = getArtifactFromMetadata(metadata, file);

		InstallRequest request = new InstallRequest();
		request.addArtifact(artifact);

		try {
			system.install(session, request);
		} catch (InstallationException e) {
			throw new MavenException("Install exception");
		}
	}

	public DormArtifact<DormMavenMetadata> getFullArtifact(DormMavenMetadata metadata) {

		// maven artifact
		Artifact artifact = new DefaultArtifact(metadata.getArtifactName());

		// create request
		CollectRequest collectRequest = new CollectRequest();
		collectRequest.setRoot(new Dependency(artifact, JavaScopes.COMPILE));
		collectRequest.addRepository(repository);

		// create dependency visitor
		DormDependencyVisitor dependencyVisitor = new DormDependencyVisitor(system, session);

		try {
			// get results from the request
			CollectResult collectResult = system.collectDependencies(session, collectRequest);

			// and attach the visitor
			collectResult.getRoot().accept(dependencyVisitor);

		} catch (DependencyCollectionException e) {
			throw new MavenException("Collect exception");
		}

		// return the root element
		return dependencyVisitor.getRoot();
	}

	public DormArtifact<DormMavenMetadata> getArtifact(DormMavenMetadata metadata) {

		// maven artifact
		Artifact artifact = getArtifactFromMetadata(metadata, null);

		ArtifactRequest request = new ArtifactRequest();
		request.setArtifact(artifact);
		request.addRepository(repository);

		try {
			ArtifactResult result = system.resolveArtifact(session, request);
			artifact = result.getArtifact();
		} catch (ArtifactResolutionException e) {
			throw new MavenException("Resolve exception");
		}

		DormArtifact<DormMavenMetadata> dormArtifact = MavenHelper.createDormArtifact(artifact);
		return dormArtifact;
	}

	private Artifact getArtifactFromMetadata(DormMavenMetadata metadata, File file) {

		Artifact artifact = new DefaultArtifact(metadata.getGroupId(), metadata.getArtifactId(), "",
				metadata.getExtension(), metadata.getVersion());

		if (null != file) {
			return artifact.setFile(file);
		}

		return artifact;
	}
}
