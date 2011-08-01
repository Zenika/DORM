//package com.zenika.dorm.maven.model.impl;
//
//import com.zenika.dorm.core.model.old.DormArtifact;
//import com.zenika.dorm.maven.model.MavenArtifact;
//
//abstract class DormMavenArtifact implements MavenArtifact {
//
//	protected DormArtifact<DormMavenMetadata> artifact;
//
//	public DormMavenArtifact(DormArtifact<DormMavenMetadata> artifact) {
//		this.artifact = artifact;
//	}
//
//	@Override
//	public String getArtifactId() {
//		return artifact.getMetadata().getExtension().getArtifactId();
//	}
//
//	@Override
//	public String getGroupId() {
//		return artifact.getMetadata().getExtension().getGroupId();
//	}
//
//	@Override
//	public String getVersion() {
//		return artifact.getMetadata().getExtension().getVersion();
//	}
//
//}
