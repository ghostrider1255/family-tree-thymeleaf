package com.javasree.spring.familytree.model;

import com.javasree.spring.familytree.model.util.RefValue;

public class TemplateBehaviour {
	
	private Long behaviourId;
	private String behaviourName;
	private String behaviourCode;
	private boolean isMultiple;
	private RefValue status;
	
	public Long getBehaviourId() {
		return behaviourId;
	}
	public void setBehaviourId(Long behaviourId) {
		this.behaviourId = behaviourId;
	}
	public String getBehaviourName() {
		return behaviourName;
	}
	public void setBehaviourName(String behaviourName) {
		this.behaviourName = behaviourName;
	}
	public String getBehaviourCode() {
		return behaviourCode;
	}
	public void setBehaviourCode(String behaviourCode) {
		this.behaviourCode = behaviourCode;
	}
	public boolean isMultiple() {
		return isMultiple;
	}
	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}
	public RefValue getStatus() {
		return status;
	}
	public void setStatus(RefValue status) {
		this.status = status;
	}
}
