package com.zenika.dorm.maven.model;

public interface MavenDependency extends MavenArtifact {

	String getScope();

	String getClassifier();

	Boolean isOptionnal();
}
