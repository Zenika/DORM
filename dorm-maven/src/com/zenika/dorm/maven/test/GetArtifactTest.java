package com.zenika.dorm.maven.test;

import java.io.File;

import com.zenika.dorm.core.model.old.DormArtifact;
import com.zenika.dorm.maven.importer.core.MavenImporterService;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;
import com.zenika.dorm.maven.model.impl.DormMavenProject;
import com.zenika.dorm.maven.pom.PomBuilder;

public class GetArtifactTest {

	public static void main(String args[]) throws Exception {

		DormMavenMetadata metadata = new DormMavenMetadata("com.zenika.demo", "demo", "1.0", "jar");

		MavenImporterService importer = new MavenImporterService();
		DormArtifact<DormMavenMetadata> artifact = importer.getFullArtifact(metadata);

		File pom = new File("target/generated-pom.xml");

		// DormArtifactMavenWrapper pomAdapter = new
		// DormArtifactMavenWrapper(artifact);

		DormMavenProject project = new DormMavenProject(artifact);
		PomBuilder builder = new PomBuilder(project, pom);
		builder.buidPom();

		
		System.out.println(builder.getPomContent());
	}
}
