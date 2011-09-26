package com.zenika.dorm.core.dao.ecr;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ecr.core.api.ClientException;
import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.DocumentModel;
import org.eclipse.ecr.core.api.DocumentModelList;
import org.eclipse.ecr.core.api.repository.Repository;
import org.eclipse.ecr.core.api.repository.RepositoryManager;
import org.eclipse.ecr.runtime.api.Framework;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.ecr.helper.EcrMetadataHelper;
import com.zenika.dorm.core.dao.ecr.helper.EcrResourceHelper;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormMetadata;

public class DormDaoEcr implements DormDao {

	@Override
	public DormMetadata get(String qualifier) {

		CoreSession session = getSession();

		// query the dorm metadata
		String query = "SELECT * FROM " + EcrResourceHelper.getDocumentNameFromExtension("dorm")
				+ " WHERE dm:qualifier = '" + qualifier + "'";

		return getOneResult(query);
	}
	
	@Override
	public DormMetadata get(String qualifier, String extension) {

		CoreSession session = getSession();

		// query the dorm metadata
		String query = "SELECT * FROM " + EcrResourceHelper.getDocumentNameFromExtension(extension)
				+ " WHERE dm:qualifier = '" + qualifier + "'";

		return getOneResult(query);
	}

	@Override
	public DormMetadata save(DormMetadata metadata) {

		CoreSession session = getSession();

		DocumentModel model = EcrMetadataHelper.toDocumentModel(metadata, session);

		try {
			model = session.createDocument(model);
			session.save();
		} catch (ClientException e) {
			throw new CoreException("Cannot save into ECR VCS");
		}

		metadata.setId(model.getId());

		return metadata;
	}

	protected List<DormMetadata> getResult(String query) {

		List<DormMetadata> result = new ArrayList<DormMetadata>();

		CoreSession session = getSession();

		try {
			DocumentModelList models = session.query(query);

			for (DocumentModel model : models) {
				DormMetadata metadata = EcrMetadataHelper.fromDocumentModel(model);
				result.add(metadata);
			}

		} catch (ClientException e) {
			throw new CoreException("Cannot execute the query : " + e.getMessage());
		} finally {
			Repository.close(session);
		}

		return result;
	}

	protected DormMetadata getOneResult(String query) {

		try {
			List<DormMetadata> result = getResult(query);
			return result.get(0);

		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	protected CoreSession getSession() {
		try {
			RepositoryManager manager = Framework.getService(RepositoryManager.class);
			return manager.getDefaultRepository().open();

		} catch (Exception e) {
			throw new CoreException("Cannot connect to the ECR repository : " + e.getMessage());
		}
	}
}