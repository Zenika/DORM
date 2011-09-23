package com.zenika.dorm.core.exception;

@SuppressWarnings("serial")
public class CoreException extends RuntimeException {
	
	private Type type;

	public CoreException(String message) {
		super(message);
	}
	
	public CoreException(String message, Exception e) {
		super(message, e);
	}
	
	public CoreException type(Type type) {
		this.type = type;
		return this;
	}
	
	public Type getType() {
		return type;
	}
	
	public static enum Type {
		NOTFOUND, ERROR
	}
}
