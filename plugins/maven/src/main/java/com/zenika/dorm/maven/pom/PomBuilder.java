package com.zenika.dorm.maven.pom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import com.zenika.dorm.maven.exception.PomException;
import com.zenika.dorm.maven.model.MavenProject;

public class PomBuilder {

	private MavenProject project;
	private File pom;

	public PomBuilder(MavenProject project, File pom) {
		this.project = project;
		this.pom = pom;
	}

	public void buidPom() {

		if (null == project || null == pom) {
			throw new PomException("pom or project are missing");
		}

		PrintWriter out = null;

		try {
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(pom), "UTF-8"));
			PomWriter writer = new PomWriter(out);
			writer.writeProject(project);

		} catch (IOException e) {
			throw new PomException("cannot write on the pom file");

		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	public String getPomContent() throws FileNotFoundException {

		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = null;

		try {
			scanner = new Scanner(new FileInputStream(pom));
			while (scanner.hasNextLine()) {
				stringBuilder.append(scanner.nextLine());
			}
		} catch (IOException e) {
			throw new PomException("cannot read the pom");

		} finally {
			if (null != scanner) {
				scanner.close();
			}
		}

		return stringBuilder.toString();
	}
}
