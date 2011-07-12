package com.zenika.dorm.maven.test;

import java.io.File;

import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public class PushArtifactTest {

	public static void main(String args[]) throws Exception {

		DormMavenMetadata metadata = new DormMavenMetadata("com.zenika.demo", "demo", "1.0", "jar");
		File file = new File("demo/demo.jar");

		MavenImporterService importer = new MavenImporterService();
		importer.installArtifact(metadata, file);

		// DormArtifact<MavenMetadata> artifact =
		// importer.getArtifactWithDependencies(metadata);
	}
}
