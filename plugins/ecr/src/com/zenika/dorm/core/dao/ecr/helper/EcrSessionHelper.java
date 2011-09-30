package com.zenika.dorm.core.dao.ecr.helper;

import org.eclipse.ecr.core.api.CoreSession;
import org.eclipse.ecr.core.api.repository.RepositoryManager;
import org.eclipse.ecr.runtime.api.Framework;

import com.zenika.dorm.core.exception.CoreException;

public final class EcrSessionHelper {
	
	private EcrSessionHelper() {
		
	}
	
	public static CoreSession getSession() {
		try {
			RepositoryManager manager = Framework.getService(RepositoryManager.class);
			return manager.getDefaultRepository().open();

		} catch (Exception e) {
			throw new CoreException("Cannot connect to the ECR repository : " + e.getMessage());
		}
	}

}