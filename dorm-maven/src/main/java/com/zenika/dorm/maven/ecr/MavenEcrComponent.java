//package com.zenika.dorm.maven.ecr;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.eclipse.ecr.runtime.api.Framework;
//import org.eclipse.ecr.runtime.model.ComponentContext;
//import org.eclipse.ecr.runtime.model.ComponentName;
//import org.eclipse.ecr.runtime.model.DefaultComponent;
//
//import com.zenika.dorm.maven.service.MavenService;
//import com.zenika.dorm.maven.service.impl.MavenServiceImpl;
//
//public class MavenEcrComponent extends DefaultComponent {
//
//	public static final ComponentName NAME = new ComponentName("com.zenika.dorm.maven.ecr.MavenEcrComponent");
//
//	private static final Log log = LogFactory.getLog(MavenEcrComponent.class);
//
//	private MavenService mavenService;
//
//	public MavenService getMavenService() {
//		return mavenService;
//	}
//
//	@Override
//	public void activate(ComponentContext context) throws Exception {
//		try {
//			mavenService = new MavenServiceImpl();
//		} catch (Exception e) {
//			log.error(e, e);
//		}
//	}
//
//	@Override
//	public void deactivate(ComponentContext context) throws Exception {
//		mavenService = null;
//	}
//
//	public static MavenEcrComponent getInstance() {
//		return (MavenEcrComponent) Framework.getRuntime().getComponent(MavenEcrComponent.NAME);
//	}
//}
