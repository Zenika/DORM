package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.model.DormMetadata;


public interface DormDao {

	public DormMetadata get(String qualifier);
	
	public DormMetadata save(DormMetadata metadata);
}
