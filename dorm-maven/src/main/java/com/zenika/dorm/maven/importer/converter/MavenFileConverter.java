package com.zenika.dorm.maven.importer.converter;

import com.zenika.dorm.core.model.old.DormFile;
import org.sonatype.aether.artifact.Artifact;

public class MavenFileConverter {

	public static DormFile artifactToFile(Artifact artifact) {
		
		DormFile file = new DormFile();
		file.setFile(artifact.getFile());
		file.setExtension(artifact.getExtension());
		file.setName(artifact.getArtifactId());
		
		return file;
	}
}
