package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.dao.ecr.DormDaoEcr;
import com.zenika.dorm.core.dao.ecr.DormIdDaoEcr;

public class DormDaoFactory {
	
	public static DormDao getDormDao() {
		return new DormDaoEcr();
	}
	
	public static DormIdDaoEcr getDormIdDao() {
		return new DormIdDaoEcr();
	}
}
