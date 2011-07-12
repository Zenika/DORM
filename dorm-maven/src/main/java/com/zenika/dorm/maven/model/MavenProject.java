package com.zenika.dorm.maven.model;

import java.util.List;

public interface MavenProject extends MavenArtifact {

	String getHeader();

	String getPackaging();

	String getName();

	String getDescription();

	String getUrl();

	List<MavenDependency> getDependencies();

	List<MavenLicense> getLicences();
}
