package com.javasree.spring.familytree.model;

import com.javasree.spring.familytree.model.util.RefValue;

public class Property {

	private Long propertyId;
	private PropertyTemplate propertyTemplate;
	private String propertyName;
	private String propertyCode;
	private RefValue propertyStatus;
	
	public Property(){
		super();
	}
	
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public PropertyTemplate getPropertyTemplate() {
		return propertyTemplate;
	}
	public void setPropertyTemplate(PropertyTemplate propertyTemplate) {
		this.propertyTemplate = propertyTemplate;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public RefValue getPropertyStatus() {
		return propertyStatus;
	}
	public void setPropertyStatus(RefValue propertyStatus) {
		this.propertyStatus = propertyStatus;
	}
}
