package com.zenika.dorm.core.ws.vo;

import com.zenika.dorm.core.model.DormArtifact;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class DormArtifactsResult {

	@XmlElementWrapper(name = "artifacts")
	@XmlElement(name = "artifact")
	private List<? extends DormArtifact> artifacts = new ArrayList<DormArtifact>();
	
	public List<? extends DormArtifact> getArtifacts() {
		return artifacts;
	}
	
	public void setArtifacts(List<? extends DormArtifact> artifacts) {
		this.artifacts = artifacts;
	}
}
