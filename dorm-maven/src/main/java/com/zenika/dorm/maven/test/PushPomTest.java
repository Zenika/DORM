package com.zenika.dorm.maven.test;

import java.io.File;

import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public class PushPomTest {

	public static void main(String[] args) throws Exception {

		DormMavenMetadata metadata = new DormMavenMetadata("com.zenika.demo", "demo", "1.0", "pom");
		File file = new File("demo/pom.xml");

		MavenImporterService importer = new MavenImporterService();
		importer.installArtifact(metadata, file);
	}
}
