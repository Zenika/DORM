package com.zenika.dorm.core.dao.ecr.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.ecr.core.api.ClientException;
import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.DocumentModel;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

public final class EcrMetadataHelper {

	private EcrMetadataHelper() {

	}

	public static DormMetadata fromDocumentModel(DocumentModel model) {

		DormMetadata metadata = new DormMetadata();
		metadata.setId(model.getId());

		String dormSchema = EcrResourceHelper.getSchemaNameFromExtension("dorm");

		metadata.setExtensionName(fromProperty(model, dormSchema, "extensionName"));
		metadata.setQualifier(fromProperty(model, dormSchema, "qualifier"));
		metadata.setVersion(fromProperty(model, dormSchema, "version"));

		String extensionSchema = EcrResourceHelper.getSchemaNameFromExtension(metadata.getExtensionName());

		Map<String, Object> ecrProperties;
		try {
			ecrProperties = model.getProperties(extensionSchema);
		} catch (Exception e) {
			throw new CoreException("Cannot get properties extension");
		}

		Map<String, String> extensionProperties = new HashMap<String, String>();

		Iterator<String> it = ecrProperties.keySet().iterator();
		while (it.hasNext()) {

			String key = it.next();

			Object property = ecrProperties.get(key);

			if (null != property) {
				extensionProperties.put(key, property.toString());
			}

		}

		metadata.setProperties(extensionProperties);
		return metadata;
	}

	public static DocumentModel toDocumentModel(DormMetadata metadata, CoreSession session) {

		DocumentModel model;

		String extension = metadata.getExtensionName();

		try {
			model = session.createDocumentModel(EcrResourceHelper.getDocumentNameFromExtension(extension));
		} catch (ClientException e) {
			throw new CoreException("Cannot create ECR document model");
		}

		try {
			Map<String, Object> dormProperties = new HashMap<String, Object>();
			dormProperties.put("extensionName", extension);
			dormProperties.put("qualifier", metadata.getQualifier());
			dormProperties.put("version", metadata.getVersion());

			model.setProperties(EcrResourceHelper.getSchemaNameFromExtension("dorm"), dormProperties);

			Map<String, Object> pluginProperties = new HashMap<String, Object>(metadata.getProperties());
			model.setProperties(extension, pluginProperties);

		} catch (Exception e) {
			throw new CoreException("Cannot map the dorm core metadatas");
		}

		return model;
	}

	private static String fromProperty(DocumentModel model, String schema, String name) {

		Object property;
		try {
			property = model.getProperty(schema, name);
		} catch (Exception e) {
			throw new CoreException("Cannot get property : " + name + " from schema : " + schema, e);
		}

		if (null == property) {
			return null;
		}

		return property.toString();
	}
}
