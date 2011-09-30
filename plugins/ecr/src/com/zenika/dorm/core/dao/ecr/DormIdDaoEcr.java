package com.zenika.dorm.core.dao.ecr;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ecr.core.api.ClientException;
import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.DocumentModel;
import org.eclipse.ecr.core.api.DocumentModelList;
import org.eclipse.ecr.core.api.repository.Repository;

import com.zenika.dorm.core.dao.ecr.helper.EcrSessionHelper;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DormId;

public class DormIdDaoEcr {

	public DormId saveFromEcr(String ecrValue) {

		DormId idFromDb = getFromEcr(ecrValue);
		if (null != idFromDb) {
			return idFromDb;
		}

		CoreSession session = EcrSessionHelper.getSession();

		Long dormValue = getNextDormId();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("ecrValue", ecrValue);
		properties.put("dormValue", dormValue);

		try {
			DocumentModel model = session.createDocumentModel("DormId");
			model.setProperties("dorm-id", properties);
			model = session.createDocument(model);
			session.save();
		} catch (ClientException e) {
			throw new CoreException("Cannot execute the query : " + e.getMessage());
		} finally {
			Repository.close(session);
		}

		return new DormId(dormValue, ecrValue);
	}

	public DormId getFromEcr(String ecrValue) {

		CoreSession session = EcrSessionHelper.getSession();
		String query = "SELECT * FROM DormId WHERE did:ecrValue = '" + ecrValue + "'";

		try {
			DocumentModelList models = session.query(query);

			if (models.isEmpty()) {
				return null;
			}

			Long dormValue = Long.valueOf(models.get(0).getProperty("dorm-id", "dormValue").toString());
			return new DormId(dormValue, ecrValue);

		} catch (ClientException e) {
			throw new CoreException("Cannot execute the query : " + e.getMessage());
		} finally {
			Repository.close(session);
		}
	}

	public Long getNextDormId() {

		CoreSession session = EcrSessionHelper.getSession();
		String query = "SELECT * FROM DormId ORDER BY did:dormValue DESC LIMIT 1";

		try {
			DocumentModelList models = session.query(query);

			if (models.isEmpty()) {
				return 1L;
			}

			return Long.valueOf(models.get(0).getProperty("dorm-id", "dormValue").toString()) + 1;

		} catch (ClientException e) {
			throw new CoreException("Cannot execute the query : " + e.getMessage());
		} finally {
			Repository.close(session);
		}
	}
}