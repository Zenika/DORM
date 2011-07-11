import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

//package com.zenika.dorm.dao.ecr;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.eclipse.ecr.core.api.ClientException;
//import org.eclipse.ecr.core.api.CoreSession;
//import org.eclipse.ecr.core.api.DocumentModel;
//import org.eclipse.ecr.core.api.DocumentModelList;
//import org.eclipse.ecr.core.api.DocumentRef;
//import org.eclipse.ecr.core.api.IdRef;
//import org.eclipse.ecr.core.api.impl.blob.FileBlob;
//import org.eclipse.ecr.core.api.repository.Repository;
//import org.eclipse.ecr.core.api.repository.RepositoryManager;
//import org.eclipse.ecr.core.storage.sql.coremodel.SQLBlob;
//import org.eclipse.ecr.runtime.api.Framework;
//
//import com.zenika.dorm.core.dao.DormDao;
//import com.zenika.dorm.core.exception.ArtifactException;
//import com.zenika.dorm.core.exception.CoreException;
//import com.zenika.dorm.core.model.DormArtifact;
//import com.zenika.dorm.core.model.DormFile;
//import com.zenika.dorm.core.model.DormMetadata;
//import com.zenika.dorm.core.model.MetadataExtension;
//
//public class DormDaoEcr implements DormDao {
//
//	@Override
//	public <T extends MetadataExtension> DormArtifact<T> create(
//			DormArtifact<T> artifact) {
//
//		CoreSession session = getSession();
//
//		DocumentModel model = getDocumentModelFromArtifact(artifact, session);
//
//		model = save(model, session);
//
//		// set the id
//		artifact.getMetadata().setId(model.getId());
//
//		return artifact;
//	}
//
//	@Override
//	public <T extends MetadataExtension> DormArtifact<T> getByMetadata(
//			DormMetadata<T> metadata) {
//
//		// query the dorm metadata
//		String query = "SELECT * FROM " + metadata.getOrigin()
//				+ " WHERE dm:name = '" + metadata.getName()
//				+ "' AND dm:version = '" + metadata.getVersion()
//				+ "' AND dm:origin = '" + metadata.getOrigin() + "'";
//
//		DormArtifact<T> artifact = getOneResult(query, metadata);
//
//		return artifact;
//	}
//
//	@Override
//	public <T extends MetadataExtension> void removeByMetadata(
//			DormMetadata<T> metadata) {
//
//		CoreSession session = getSession();
//
//		DormArtifact<T> artifact = getByMetadata(metadata);
//
//		DocumentRef ref = new IdRef(artifact.getMetadata().getId());
//
//		try {
//			session.removeDocument(ref);
//		} catch (ClientException e) {
//			throw new ArtifactException("Cannot remove the ECR document : "
//					+ e.getMessage()).type(ArtifactException.Type.ERROR);
//		}
//	}
//
//	protected DocumentModel save(DocumentModel model, CoreSession session) {
//
//		try {
//			model = session.createDocument(model);
//			session.save();
//		} catch (ClientException e) {
//			throw new CoreException("Cannot save into ECR VCS");
//		}
//
//		return model;
//	}
//
//	protected <T extends MetadataExtension> List<DormArtifact<T>> getResult(
//			String query, DormMetadata<T> metadata) {
//
//		List<DormArtifact<T>> result = new ArrayList<DormArtifact<T>>();
//
//		CoreSession session = getSession();
//
//		try {
//			// get the query's result
//			DocumentModelList list = session.query(query);
//
//			// map the result to the list
//			for (DocumentModel model : list) {
//				result.add(getArtifactFromDocumentModel(model, metadata));
//			}
//
//		} catch (ClientException e) {
//			throw new CoreException("Cannot execute the query : "
//					+ e.getMessage());
//		} finally {
//			Repository.close(session);
//		}
//
//		return result;
//	}
//
//	protected <T extends MetadataExtension> DormArtifact<T> getOneResult(
//			String query, DormMetadata<T> metadata) {
//
//		try {
//			List<DormArtifact<T>> result = getResult(query, metadata);
//			return result.get(0);
//
//		} catch (IndexOutOfBoundsException e) {
//			throw new ArtifactException("Artifact not found with query : "
//					+ query).type(ArtifactException.Type.NULL);
//		}
//	}
//
//
//protected <T extends MetadataExtension> DocumentModel getDocumentModelFromArtifact(
//			DormArtifact<T> artifact, CoreSession session) {
//
//		DocumentModel model;
//
//		try {
//			model = session.createDocumentModel(artifact.getMetadata()
//					.getOrigin());
//		} catch (ClientException e) {
//			throw new CoreException("Cannot create or map ECR document model");
//		}
//
//		try {
//			mapDormArtifact(artifact, model);
//			mapSpecificMetadatas(artifact.getMetadata(), model);
//
//		} catch (Exception e) {
//			throw new CoreException("Cannot map the dorm core metadatas");
//		}
//
//		return model;
//	}
//
//	protected <T extends MetadataExtension> void mapDormArtifact(
//			DormArtifact<T> artifact, DocumentModel model) {
//
//		Map<String, Object> properties = new HashMap<String, Object>();
//
//		properties.put("name", artifact.getMetadata().getName());
//		properties.put("version", artifact.getMetadata().getVersion());
//		properties.put("origin", artifact.getMetadata().getOrigin());
//
//		// get the file if exists
//		if (null != artifact.getFile()) {
//
//			FileBlob blob = new FileBlob(artifact.getFile().getFile());
//			blob.setFilename(artifact.getFile().getName());
//
//			properties.put("binary", blob);
//		}
//
//		try {
//			// map properties to the schema
//			model.setProperties("dorm", properties);
//		} catch (ClientException e) {
//			throw new CoreException("Cannot map metadatas to the dorm schema");
//		}
//	}
//
//	protected <T extends MetadataExtension> void mapSpecificMetadatas(
//			DormMetadata<T> metadata, DocumentModel model)
//			throws IllegalAccessException, IllegalArgumentException,
//			NoSuchFieldException {
//
//		if (null == metadata.getExtension()) {
//			return;
//		}
//
//		T extension = metadata.getExtension();
//
//		Class<? extends MetadataExtension> reflect = extension.getClass();
//
//		Map<String, Object> properties = new HashMap<String, Object>();
//
//		for (Field field : reflect.getDeclaredFields()) {
//
//			// include non public attributes
//			field.setAccessible(true);
//
//			// TODO: Add required field checking, by annotation for example ?
//
//			// get the field value
//			Object value = field.get(extension);
//
//			// file metadata
//			if (Field.class.equals(field.getType()) && null != value) {
//
//				FileBlob blob = new FileBlob((File) value);
//
//				properties.put(field.getName(), blob);
//			}
//
//			else {
//				// add metadata fields
//				properties.put(field.getName(), value);
//			}
//
//		}
//
//		try {
//			model.setProperties("dorm-maven", properties);
//		} catch (ClientException e) {
//			throw new CoreException("Cannot map metadatas to the ECR schema");
//		}
//	}
//
//	/**
//	 * TODO: Set the metadataExtensionClass in a local thread maybe ?
//	 *
//	 * @param <T> the metadata extension generic type
//	 * @param model the ECR document model
//	 * @param metadataExtensionClass the metadata extension class
//	 *
//	 * @return {@link DormArtifact}<T> the dorm artifact
//	 */
//	protected <T extends MetadataExtension> DormArtifact<T> getArtifactFromDocumentModel(
//			DocumentModel model, DormMetadata<T> metadata) {
//
//		// create the new dorm artifact that will contain the newly mapped
//		// metadata
//		DormArtifact<T> artifact = new DormArtifact<T>();
//
//		// create the new dorm metadata that will be mapped with the document
//		// model
//		DormMetadata<T> mappedMetadata = mapDocumentToDormMetadata(model,
//				metadata);
//		artifact.setMetadata(mappedMetadata);
//
//		DormFile file = mapDocumentModelToDormFile(model);
//		artifact.setFile(file);
//
//		// create and map metadata extension if exists
//		if (null != metadata.getExtension()) {
//			try {
//				// get the mapped metadata extension
//				T metadataExtension = mapDocumentModelToMetadataExtension(
//						model, metadata);
//
//				// and set it to the mapped metadata
//				mappedMetadata.setExtension(metadataExtension);
//
//			} catch (Exception e) {
//				throw new CoreException(
//						"Cannot map from ECR document model to metadata extension");
//			}
//		}
//
//		return artifact;
//	}
//
//	protected <T extends MetadataExtension> DormMetadata<T> mapDocumentToDormMetadata(
//			DocumentModel model, DormMetadata<T> metadata) {
//
//		DormMetadata<T> mappedMetadata = new DormMetadata<T>();
//
//		try {
//			// dorm schema mapping
//			mappedMetadata.setId(model.getId());
//			mappedMetadata
//					.setName(model.getProperty("dorm", "name").toString());
//			mappedMetadata.setVersion(model.getProperty("dorm", "version")
//					.toString());
//			mappedMetadata.setOrigin(model.getProperty("dorm", "origin")
//					.toString());
//
//		} catch (ClientException e) {
//			throw new CoreException(
//					"Cannot map from ECR document model dorm schema to dorm artifact");
//		} catch (NullPointerException e) {
//			throw new CoreException(
//					"Empty requiered informations in the ECR document model");
//		}
//
//		return mappedMetadata;
//	}
//
//	/**
//	 * Get the DormFile from the document model dorm schema
//	 *
//	 * @param <T>
//	 * @param model
//	 * @return
//	 */
//	protected DormFile mapDocumentModelToDormFile(DocumentModel model) {
//
//		Object ecrFile;
//
//		try {
//			ecrFile = model.getProperty("dorm", "binary");
//		} catch (ClientException e1) {
//			throw new CoreException(
//					"Cannot get the dorm binary property from ECR document model");
//		}
//
//		DormFile dormFile = null;
//
//		if (null != ecrFile) {
//			try {
//				SQLBlob blob = ((SQLBlob) ecrFile);
//
//				// TODO: get the file from blob
//				dormFile = new DormFile();
//
//			} catch (ClassCastException e) {
//				// should not happen
//				// TODO: log the exception
//			}
//		}
//
//		return dormFile;
//	}
//
//	protected <T extends MetadataExtension> T mapDocumentModelToMetadataExtension(
//			DocumentModel model, DormMetadata<T> metadata)
//			throws IllegalArgumentException, IllegalAccessException {
//
//		T metadataExtension;
//
//		try {
//			// TODO: Correct this if possible ?
//			@SuppressWarnings("unchecked")
//			Class<T> extensionClass = (Class<T>) metadata.getExtension()
//					.getClass();
//
//			// create new instance of metadata extension by reflection
//			metadataExtension = extensionClass.newInstance();
//		} catch (Exception e) {
//			throw new CoreException(
//					"Cannot create new instance of metadata extension");
//		}
//
//		Class<? extends MetadataExtension> reflect = metadataExtension
//				.getClass();
//
//		for (Field field : reflect.getDeclaredFields()) {
//
//			// include non public attributes
//			field.setAccessible(true);
//
//			// get the property matching the field's name
//			Object property;
//			try {
//				property = model.getProperty("dorm-maven", field.getName());
//			} catch (ClientException e) {
//				throw new CoreException(
//						"Cannot get property from ECR document model");
//			}
//
//			// and set it in the metadata
//			field.set(metadataExtension, property);
//		}
//
//		return metadataExtension;
//	}
//
//	protected CoreSession getSession() {
//		try {
//			// connect to the repository
//			RepositoryManager manager = Framework
//					.getService(RepositoryManager.class);
//			return manager.getDefaultRepository().open();
//
//		} catch (Exception e) {
//			throw new CoreException("Cannot connect to the ECR repository : "
//					+ e.getMessage());
//		}
//	}
//}