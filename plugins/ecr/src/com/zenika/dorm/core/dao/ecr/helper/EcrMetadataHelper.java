package com.zenika.dorm.core.dao.ecr.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.ecr.core.api.ClientException;
import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.DocumentModel;

import com.zenika.dorm.core.dao.DormDaoFactory;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormId;
import com.zenika.dorm.core.model.DormMetadata;

public final class EcrMetadataHelper {

	private EcrMetadataHelper() {

	}

	public static DormMetadata fromDocumentModel(DocumentModel model) throws ClientException {

		DormMetadata metadata = new DormMetadata();

		DormId id = DormDaoFactory.getDormIdDao().getFromEcr(model.getId());
		metadata.setId(id.getDormValue());

		String dormSchema = EcrResourceHelper.getSchemaNameFromExtension("dorm");

		metadata.setExtensionName(fromProperty(model, dormSchema, "extensionName"));
		metadata.setName(fromProperty(model, dormSchema, "name"));
		metadata.setVersion(fromProperty(model, dormSchema, "version"));

		String extensionSchema = EcrResourceHelper.getSchemaNameFromExtension(metadata.getExtensionName());

		Map<String, Object> ecrProperties = model.getProperties(extensionSchema);
		Map<String, String> extensionProperties = new HashMap<String, String>();

		Iterator<String> it = ecrProperties.keySet().iterator();
		while (it.hasNext()) {

			String key = it.next();
			Object property = ecrProperties.get(key);

			if (null != property) {
				String keyWithoutPrefix = key.substring(key.indexOf(":") + 1);
				extensionProperties.put(keyWithoutPrefix, property.toString());
			}

		}

		metadata.setProperties(extensionProperties);
		return metadata;
	}

	public static DocumentModel toDocumentModel(DormMetadata metadata, CoreSession session) throws ClientException {

		String extension = metadata.getExtensionName();

		DocumentModel model = session.createDocumentModel(EcrResourceHelper.getDocumentNameFromExtension(extension));

		Map<String, Object> dormProperties = new HashMap<String, Object>();
		dormProperties.put("extensionName", extension);
		dormProperties.put("name", metadata.getName());
		dormProperties.put("version", metadata.getVersion());

		model.setProperties(EcrResourceHelper.getSchemaNameFromExtension("dorm"), dormProperties);

		Map<String, Object> pluginProperties = new HashMap<String, Object>(metadata.getProperties());
		model.setProperties(EcrResourceHelper.getSchemaNameFromExtension(extension), pluginProperties);

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
