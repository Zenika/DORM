package com.zenika.dorm.core.ws.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.zenika.dorm.core.ws.rs.resource.DormResource;

public class DormApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(DormResource.class);
		return set;
	}
}