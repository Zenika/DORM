//package com.zenika.dorm.maven.importer.core;
//
//import java.util.LinkedList;
//
//import org.sonatype.aether.RepositorySystem;
//import org.sonatype.aether.RepositorySystemSession;
//import org.sonatype.aether.artifact.Artifact;
//import org.sonatype.aether.graph.DependencyNode;
//import org.sonatype.aether.graph.DependencyVisitor;
//import org.sonatype.aether.resolution.ArtifactRequest;
//import org.sonatype.aether.resolution.ArtifactResolutionException;
//import org.sonatype.aether.resolution.ArtifactResult;
//
//import com.zenika.dorm.core.model.old.DormArtifact;
//import com.zenika.dorm.maven.exception.MavenException;
//import com.zenika.dorm.maven.helper.MavenHelper;
//import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
//
//public class DormDependencyVisitor implements DependencyVisitor {
//
//	private RepositorySystem system;
//	private RepositorySystemSession session;
//
//	private LinkedList<DormArtifact<DormMavenMetadata>> tree;
//
//	public DormDependencyVisitor(RepositorySystem system, RepositorySystemSession session) {
//
//		this.system = system;
//		this.session = session;
//
//		tree = new LinkedList<DormArtifact<DormMavenMetadata>>();
//	}
//
//	public boolean visitEnter(DependencyNode node) {
//
//		System.out.println("ENTER " + node.getDependency().getArtifact().getArtifactId());
//
//		Artifact mavenArtifact;
//
//		try {
//			// resolve the dependency
//			ArtifactRequest request = new ArtifactRequest(node);
//			ArtifactResult result = system.resolveArtifact(session, request);
//			mavenArtifact = result.getArtifact();
//
//		} catch (ArtifactResolutionException e) {
//			throw new MavenException("Resolve artifact exception");
//		}
//
//		DormArtifact<DormMavenMetadata> current = MavenHelper.createDormArtifact(mavenArtifact);
//
//		if (!tree.isEmpty()) {
//			tree.getLast().getDependencies().add(current);
//		}
//
//		tree.add(current);
//
//		return true;
//	}
//
//	public boolean visitLeave(DependencyNode node) {
//
//		System.out.println("LEAVE " + node.getDependency().getArtifact().getArtifactId());
//
//		// remove the current element from dependency tree if this element have
//		// no more child
//		if (tree.size() > 1) {
//			tree.removeLast();
//		}
//
//		return true;
//	}
//
//	public DormArtifact<DormMavenMetadata> getRoot() {
//		return tree.getLast();
//	}
//}
