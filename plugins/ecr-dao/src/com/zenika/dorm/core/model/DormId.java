package com.zenika.dorm.core.model;

public class DormId {

	private Long dormValue;
	private String ecrValue;

	public DormId(Long dormValue, String ecrValue) {
		this.dormValue = dormValue;
		this.ecrValue = ecrValue;
	}

	public Long getDormValue() {
		return dormValue;
	}

	public void setDormValue(Long dormValue) {
		this.dormValue = dormValue;
	}

	public String getEcrValue() {
		return ecrValue;
	}

	public void setEcrValue(String ecrValue) {
		this.ecrValue = ecrValue;
	}
}
