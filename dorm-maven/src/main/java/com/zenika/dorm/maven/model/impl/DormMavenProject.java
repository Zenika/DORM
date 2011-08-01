//package com.zenika.dorm.maven.model.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.zenika.dorm.core.helper.DormDependencyHelper;
//import com.zenika.dorm.core.model.old.DormArtifact;
//import com.zenika.dorm.maven.model.MavenDependency;
//import com.zenika.dorm.maven.model.MavenLicense;
//import com.zenika.dorm.maven.model.MavenProject;
//
//public class DormMavenProject extends DormMavenArtifact implements MavenProject {
//
//	private List<MavenDependency> dependencies;
//	private List<MavenLicense> licenses;
//
//	public DormMavenProject(DormArtifact<DormMavenMetadata> artifact) {
//		super(artifact);
//		dependencies = new ArrayList<MavenDependency>();
//	}
//
//	@Override
//	public String getHeader() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getPackaging() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getDescription() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getUrl() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<MavenLicense> getLicences() {
//		return licenses;
//	}
//
//	/**
//	 * Lazy load dependencies list
//	 */
//	@Override
//	public List<MavenDependency> getDependencies() {
//
//		if (dependencies.isEmpty()) {
//
//			List<DormArtifact<DormMavenMetadata>> dormDependencies = DormDependencyHelper
//					.getDependenciesDeepList(artifact);
//
//			for (DormArtifact<DormMavenMetadata> dependency : dormDependencies) {
//				dependencies.add(new DormMavenDependency(dependency));
//			}
//		}
//
//		return dependencies;
//	}
//
//}
