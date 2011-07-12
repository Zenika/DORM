package com.zenika.dorm.maven.pom;

import java.io.PrintWriter;

import com.zenika.dorm.maven.model.MavenArtifact;
import com.zenika.dorm.maven.model.MavenDependency;
import com.zenika.dorm.maven.model.MavenLicense;
import com.zenika.dorm.maven.model.MavenProject;

class PomWriter {

	private PrintWriter writer;

	public PomWriter(PrintWriter writer) {
		this.writer = writer;
	}

	/**
	 * Proxy to ident(1)
	 */
	public void ident() {
		ident(1);
	}

	public void ident(int idents) {
		for (int i = 0; i < idents; i++) {
			writer.print("\t");
		}
	}

	public void writeArtifact(MavenArtifact artifact, int idents) {

		ident(idents);
		writer.println("<groupId>" + artifact.getGroupId() + "</groupId>");

		ident(idents);
		writer.println("<artifactId>" + artifact.getArtifactId() + "</artifactId>");

		ident(idents);
		writer.println("<version>" + artifact.getVersion() + "</version>");
	}

	public void writeDependency(MavenDependency dependency) {

		ident(3);
		writer.println("<dependency>");

		writeArtifact(dependency, 4);

		if (null != dependency.getScope()) {
			ident(4);
			writer.println("<scope>" + dependency.getScope() + "</scope>");
		}

		if (null != dependency.getClassifier()) {
			ident(4);
			writer.println("<classifier>" + dependency.getClassifier() + "</classifier>");
		}

		ident(3);
		writer.println("</dependency>");
	}

	public void writeProject(MavenProject project) {

		if (null != project.getHeader()) {
			writer.println("\n<!--");
			writer.println(project.getHeader());
			writer.println("-->\n");
		}

		writer.println("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		writer.println("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">");

		ident();
		writer.println("<modelVersion>4.0.0</modelVersion>");

		writeArtifact(project, 1);

		if (null != project.getPackaging()) {
			ident();
			writer.println("<packaging>" + project.getPackaging() + "</packaging");
		}

		if (null != project.getDescription()) {
			writer.println();
			ident();
			writer.println("<description>");

			ident(2);
			writer.println(project.getPackaging());

			ident();
			writer.println("</description");
		}

		if (null != project.getLicences() && !project.getLicences().isEmpty()) {

			writer.println();
			ident();
			writer.println("<licenses>");

			for (MavenLicense license : project.getLicences()) {

				ident(2);
				writer.println("<license>");

				ident(3);
				writer.println("<name>" + license.getName() + "</name>");

				ident(3);
				writer.println("<url>" + license.getUrl() + "</url>");

				ident(3);
				writer.println("<distribution>" + license.getDistribution() + "</distribution>");

				ident(3);
				writer.println("<comments>" + license.getComment() + "</comments>");

				ident(2);
				writer.println("</license>");
			}

			ident();
			writer.println("</licenses>");
		}

		if (null != project.getDependencies() && !project.getDependencies().isEmpty()) {

			ident(2);
			writer.println("<dependencies>");

			for (MavenDependency dependency : project.getDependencies()) {
				writeDependency(dependency);
			}

			ident(2);
			writer.println("</dependencies>");
		}

		writer.println("</project>");
	}
}
