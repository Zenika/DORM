package com.zenika.dorm.core.helper;

import java.util.ArrayList;
import java.util.List;

import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.MetadataExtension;

public class DormDependencyHelper {

	public static <T extends MetadataExtension> List<DormArtifact<T>> getDependenciesDeepList(DormArtifact<T> artifact) {

		List<DormArtifact<T>> list = new ArrayList<DormArtifact<T>>();

		for (DormArtifact<T> dependency : artifact.getDependencies()) {

			// add the element
			list.add(dependency);

			// get all element's dependencies by recursive call
			List<DormArtifact<T>> deepDependencies = getDependenciesDeepList(dependency);

			// and add these dependencies
			list.addAll(deepDependencies);
		}

		return list;
	}
}
