package com.zenika.dorm.core.dao;

import com.zenika.dorm.core.model.DormMetadata;


public interface DormDao {
	
	public DormMetadata save(DormMetadata metadata);

	public DormMetadata get(String extensionName, String name, String version);
}
