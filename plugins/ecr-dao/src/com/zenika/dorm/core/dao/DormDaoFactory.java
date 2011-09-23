package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.dao.ecr.DormDaoEcr;

public class DormDaoFactory {
	
	public static DormDao getDormDao() {
		return new DormDaoEcr();
	}
}
