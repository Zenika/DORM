package com.zenika.dorm.maven.helper;

import org.sonatype.aether.artifact.Artifact;

import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.maven.model.impl.DormMavenMetadata;

public class MavenArtifactHelper {

	public static DormArtifact<DormMavenMetadata> createDormArtifact(Artifact mavenArtifact) {

		// maven metadata
		DormMavenMetadata mavenMetadata = new DormMavenMetadata(mavenArtifact.getGroupId(), mavenArtifact.getArtifactId(),
				mavenArtifact.getVersion(), mavenArtifact.getExtension());

		// dorm metadata
		DormMetadata<DormMavenMetadata> dormMetadata = createDormMetadata(mavenMetadata);

		// dorm file
		DormFile dormFile = DormFileHelper.createDormFile(dormMetadata, mavenArtifact.getFile(),
				mavenArtifact.getExtension());

		// dorm artifact
		DormArtifact<DormMavenMetadata> dormArtifact = new DormArtifact<DormMavenMetadata>(dormMetadata, dormFile);
		dormArtifact.setMetadata(dormMetadata);
		dormArtifact.setFile(dormFile);

		return dormArtifact;
	}

	public static DormMetadata<DormMavenMetadata> createDormMetadata(DormMavenMetadata mavenMetadata) {

		// define the mapping between dorm and maven metadatas
		String name = mavenMetadata.getGroupId() + ":" + mavenMetadata.getArtifactId() + ":"
				+ mavenMetadata.getVersion() + ":" + mavenMetadata.getExtension();

		// create new dorm metadata from maven
		DormMetadata<DormMavenMetadata> metadata = new DormMetadata<DormMavenMetadata>(name, mavenMetadata.getVersion(),
				DormMavenMetadata.ORIGIN);

		// set the maven extension
		metadata.setExtension(mavenMetadata);

		return metadata;
	}
}
