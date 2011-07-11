//package com.zenika.dorm.dao.ecr;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.eclipse.ecr.runtime.model.ComponentContext;
//import org.eclipse.ecr.runtime.model.ComponentName;
//import org.eclipse.ecr.runtime.model.DefaultComponent;
//
//import com.zenika.dorm.core.service.DormService;
//import com.zenika.dorm.core.service.impl.DormServiceImpl;
//
//public class DormEcrComponent extends DefaultComponent {
//
//	public static final ComponentName NAME = new ComponentName(
//			"com.zenika.dorm.core.ecr.DormEcrComponent");
//
//	private static final Log log = LogFactory.getLog(DormEcrComponent.class);
//
//	private DormService dormService;
//
//	public DormService getDormService() {
//		return dormService;
//	}
//
//	@Override
//	public void activate(ComponentContext context) throws Exception {
//		try {
//			dormService = new DormServiceImpl();
//		} catch (Exception e) {
//			log.error(e, e);
//		}
//	}
//
//	@Override
//	public void deactivate(ComponentContext context) throws Exception {
//		dormService = null;
//	}
//}
