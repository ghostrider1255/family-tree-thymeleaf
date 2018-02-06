package com.javasree.spring.familytree.model;

import com.javasree.spring.familytree.model.util.RefValue;

public class TemplateGroup {
	
	private Long groupId;
	private String groupName;
	private String groupCode;
	private RefValue status;
	
	public TemplateGroup(){
		super();
	}
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public RefValue getStatus() {
		return status;
	}
	public void setStatus(RefValue status) {
		this.status = status;
	}
	
}
