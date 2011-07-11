package com.zenika.dorm.core.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.zenika.dorm.core.model.DormArtifact;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class DormArtifactResult  {

	private DormArtifact artifact;

	public DormArtifact getArtifact() {
		return artifact;
	}

	public void setArtifact(DormArtifact artifact) {
		this.artifact = artifact;
	}
}
