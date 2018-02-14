package com.javasree.spring.familytree.model.profile;

public class CustomeProfile extends Profile{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7068654423919256236L;
	
	private Long customeProfileId;
	private String childOf;
	private Long numberOfChildren;
	private Long age;
	private Long numberOfCelebratedAnniversaries;
	private String maritalStatus;
	
	public String getChildOf() {
		return childOf;
	}
	public void setChildOf(String childOf) {
		this.childOf = childOf;
	}
	public Long getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(Long numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Long getNumberOfCelebratedAnniversaries() {
		return numberOfCelebratedAnniversaries;
	}
	public void setNumberOfCelebratedAnniversaries(Long numberOfCelebratedAnniversaries) {
		this.numberOfCelebratedAnniversaries = numberOfCelebratedAnniversaries;
	}
	public Long getCustomeProfileId() {
		return customeProfileId;
	}
	public void setCustomeProfileId(Long customeProfileId) {
		this.customeProfileId = customeProfileId;
	}

}
