package com.zenika.dorm.maven.test;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public class GetPomTest {
	public static void main(String[] args) {
		
		DormMavenMetadata metadata = new DormMavenMetadata("com.zenika.demo", "demo", "1.0", "pom");

		MavenImporterService importer = new MavenImporterService();
		DormArtifact<DormMavenMetadata> artifact = importer.getArtifact(metadata);
	}
}
