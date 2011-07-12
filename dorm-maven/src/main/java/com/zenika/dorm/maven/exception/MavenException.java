package com.zenika.dorm.maven.exception;

import com.zenika.dorm.core.exception.ArtifactException;

@SuppressWarnings("serial")
public class MavenException extends ArtifactException {

	public MavenException(String message) {
		super(message);
	}
}
