package com.javasree.spring.familytree.model;

public class PropertyTemplate {
	
	private Long propertyId;
	private String name;
	private String description;
	private String group;
	private Long propertyOrder;
	private Long maxLength;
	private TemplateGroup templateGroup;
	private TemplateBehaviour templateBehaviour;
	private boolean status;
	
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Long getPropertyOrder() {
		return propertyOrder;
	}
	public void setPropertyOrder(Long propertyOrder) {
		this.propertyOrder = propertyOrder;
	}
	public Long getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public TemplateGroup getTemplateGroup() {
		return templateGroup;
	}
	public void setTemplateGroup(TemplateGroup templateGroup) {
		this.templateGroup = templateGroup;
	}
	public TemplateBehaviour getTemplateBehaviour() {
		return templateBehaviour;
	}
	public void setTemplateBehaviour(TemplateBehaviour templateBehaviour) {
		this.templateBehaviour = templateBehaviour;
	}
}
