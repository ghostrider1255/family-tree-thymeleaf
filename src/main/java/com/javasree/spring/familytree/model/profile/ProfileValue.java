package com.javasree.spring.familytree.model.profile;

import java.io.Serializable;

import com.javasree.spring.familytree.model.PropertyTemplate;

public class ProfileValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5999879481503393075L;
	private Long profileValueId;
	private String value;
	private PropertyTemplate propertyTemplate;
	
	public Long getProfileValueId() {
		return profileValueId;
	}
	public void setProfileValueId(Long profileValueId) {
		this.profileValueId = profileValueId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public PropertyTemplate getProperty() {
		return propertyTemplate;
	}
	public void setProperty(PropertyTemplate propertyTemplate) {
		this.propertyTemplate = propertyTemplate;
	}
	
}
