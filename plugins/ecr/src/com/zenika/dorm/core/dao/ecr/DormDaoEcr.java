package com.zenika.dorm.core.dao.ecr;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ecr.core.api.ClientException;
import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.DocumentModel;
import org.eclipse.ecr.core.api.DocumentModelList;
import org.eclipse.ecr.core.api.repository.Repository;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.DormDaoFactory;
import com.zenika.dorm.core.dao.ecr.helper.EcrMetadataHelper;
import com.zenika.dorm.core.dao.ecr.helper.EcrResourceHelper;
import com.zenika.dorm.core.dao.ecr.helper.EcrSessionHelper;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.exception.CoreException.Type;
import com.zenika.dorm.core.model.DormId;
import com.zenika.dorm.core.model.DormMetadata;

public class DormDaoEcr implements DormDao {

	@Override
	public DormMetadata get(String extensionName, String name, String version) {

		String query = "SELECT * FROM " + EcrResourceHelper.getDocumentNameFromExtension(extensionName)
				+ " WHERE dm:name = '" + name + "' AND dm:version= '" + version + "'";

		return getOneResult(query);
	}

	@Override
	public DormMetadata save(DormMetadata metadata) {

		DormMetadata metadataFromDb = get(metadata.getExtensionName(), metadata.getName(), metadata.getVersion());

		if (null != metadataFromDb) {
			return metadataFromDb;
		}

		CoreSession session = EcrSessionHelper.getSession();
		DocumentModel model;

		try {
			model = EcrMetadataHelper.toDocumentModel(metadata, session);
			model = session.createDocument(model);
			session.save();
		} catch (ClientException e) {
			throw new CoreException("Cannot save into ECR VCS", e).type(Type.ERROR);
		} finally {
			Repository.close(session);
		}
		
		DormId id = DormDaoFactory.getDormIdDao().saveFromEcr(model.getId());
		metadata.setId(id.getDormValue());

		return metadata;
	}

	protected List<DormMetadata> getResult(String query) {

		List<DormMetadata> result = new ArrayList<DormMetadata>();

		CoreSession session = EcrSessionHelper.getSession();

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
}