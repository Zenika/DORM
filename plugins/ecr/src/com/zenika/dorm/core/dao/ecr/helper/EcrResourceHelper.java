package com.zenika.dorm.core.dao.ecr.helper;

public final class EcrResourceHelper {

	private EcrResourceHelper() {

	}
	
	public static String getSchemaNameFromExtension(String extension) {
		return extension + "-metadata";
	}
	
	public static String getDocumentNameFromExtension(String extension) {
		return extension.substring(0, 1).toUpperCase() + extension.substring(1) + "Metadata";
	}

}
