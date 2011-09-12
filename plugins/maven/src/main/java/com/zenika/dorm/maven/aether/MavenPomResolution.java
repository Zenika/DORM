package com.zenika.dorm.maven.aether;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.repository.internal.DefaultArtifactDescriptorReader;
import org.apache.maven.repository.internal.DefaultVersionRangeResolver;
import org.apache.maven.repository.internal.DefaultVersionResolver;
import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.codehaus.plexus.PlexusContainerException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.connector.wagon.WagonRepositoryConnectorFactory;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.impl.ArtifactDescriptorReader;
import org.sonatype.aether.impl.VersionRangeResolver;
import org.sonatype.aether.impl.VersionResolver;
import org.sonatype.aether.impl.internal.DefaultServiceLocator;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactDescriptorRequest;
import org.sonatype.aether.resolution.ArtifactDescriptorResult;
import org.sonatype.aether.resolution.DependencyRequest;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.sonatype.aether.util.artifact.ArtifactProperties;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.graph.PreorderNodeListGenerator;

public class MavenPomResolution {

	public void foo() throws Exception {

		// RepositorySystem repoSystem = newRepositorySystem();
		// DefaultRepositorySystem repoSystem = new DefaultRepositorySystem();
		// LocalRepositoryManagerFactory factory = new
		// SimpleLocalRepositoryManagerFactory();
		// repoSystem.addLocalRepositoryManagerFactory(factory);
		//
		// DefaultDependencyCollector dependencyCollector = new
		// DefaultDependencyCollector();
		//
		// DefaultVersionRangeResolver versionRangeResolver = new
		// DefaultVersionRangeResolver();
		// dependencyCollector.setVersionRangeResolver(versionRangeResolver);
		//
		// DefaultArtifactDescriptorReader artifactDescriptorReader = new
		// DefaultArtifactDescriptorReader();
		//
		// DefaultVersionResolver versionResolver = new
		// DefaultVersionResolver();
		// artifactDescriptorReader.setVersionResolver(versionResolver);
		//
		// DefaultArtifactResolver artifactResolver = new
		// DefaultArtifactResolver();
		//
		// DefaultSyncContextFactory syncContextFactory = new
		// DefaultSyncContextFactory();
		// artifactResolver.setSyncContextFactory(syncContextFactory);
		//
		// artifactDescriptorReader.setArtifactResolver(artifactResolver);
		//
		// dependencyCollector
		// .setArtifactDescriptorReader(artifactDescriptorReader);
		//
		// repoSystem.setDependencyCollector(dependencyCollector);
		//
		// MavenRepositorySystemSession session = new
		// MavenRepositorySystemSession();
		//
		// LocalRepository localRepo = new LocalRepository("target/local-repo");
		//
		// session.setLocalRepositoryManager(repoSystem
		// .newLocalRepositoryManager(localRepo));

		RepositorySystem repoSystem = newRepositorySystem();
		RepositorySystemSession session = newSession(repoSystem);

		// File file = new File("tmp/repo/maven-profile-2.2.1.pom");

		DefaultArtifact artifact = new DefaultArtifact("org.apache.maven",
				"maven-profile", "pom", "2.2.1");

		ArtifactDescriptorRequest descriptorRequest = new ArtifactDescriptorRequest();
		descriptorRequest.setArtifact(artifact);

		ArtifactDescriptorResult artifactDescriptorResult = repoSystem
				.readArtifactDescriptor(session, descriptorRequest);

		// DefaultArtifact artifact = new DefaultArtifact(
		// "org.apache.maven:maven-profile:2.2.1");
		// Map<String, String> map = new HashMap<String, String>();
		// map.put(ArtifactProperties.LOCAL_PATH, "tmp/repo");
		//
		// Artifact artifact2 = artifact.setProperties(map);
		//
		// Dependency dependency = new Dependency(artifact, "compile");
		//
		// RemoteRepository central = new RemoteRepository("central", "default",
		// "http://repo1.maven.org/maven2/");
		//
		// LocalRepository local = new LocalRepository("/tmp/repo");
		//
		// CollectRequest collectRequest = new CollectRequest();
		// collectRequest.setRoot(dependency);
		// collectRequest.addRepository(local);
		//
		// DependencyNode node = repoSystem.collectDependencies(session,
		// collectRequest).getRoot();
		//
		// DependencyRequest dependencyRequest = new DependencyRequest(node,
		// null);
		//
		// repoSystem.resolveDependencies(session, dependencyRequest);
		//
		// PreorderNodeListGenerator nlg = new PreorderNodeListGenerator();
		// node.accept(nlg);
		// System.out.println(nlg.getClassPath());
	}

	private static RepositorySystemSession newSession(RepositorySystem system) {
		MavenRepositorySystemSession session = new MavenRepositorySystemSession();

		LocalRepository localRepo = new LocalRepository("tmp/repo");
		session.setLocalRepositoryManager(system
				.newLocalRepositoryManager(localRepo));

		return session;
	}

	private static RepositorySystem newRepositorySystem()
			throws PlexusContainerException {
		DefaultServiceLocator locator = new DefaultServiceLocator();

		// PlexusWagonProvider wagonProvider = new PlexusWagonProvider();
		//
		// HttpWagon httpWagon = new HttpWagon();
		//
		// DefaultPlexusContainer plexusContainer = new
		// DefaultPlexusContainer();
		//
		// locator.setServices( WagonProvider.class, wagonProvider);
		locator.addService(RepositoryConnectorFactory.class,
				WagonRepositoryConnectorFactory.class);
		locator.addService(VersionResolver.class, DefaultVersionResolver.class);
		locator.addService(VersionRangeResolver.class,
				DefaultVersionRangeResolver.class);
		locator.addService(ArtifactDescriptorReader.class,
				DefaultArtifactDescriptorReader.class);

		return locator.getService(RepositorySystem.class);
	}

	public static void main(String args[]) throws Exception {
		MavenPomResolution pomResolution = new MavenPomResolution();
		pomResolution.foo();
	}

	public void bar() {

	}
}
