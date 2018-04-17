package com.javasree.spring.familytree.web.dto;

import java.util.Date;

public class CustomeProfile{

	private Long customeProfileId;
	private String profileName;
	private String firstName;
	private String lastName;
	private String gender;
	private Date dateOfBirth;
	private Date dateOfDeath;
	private Date marriageAnniversary;
	private boolean lifePartner;
	private Long parentId;
	private String childOf;
	private Long numberOfChildren;
	private Long age;
	private Long numberOfCelebratedAnniversaries;
	private String maritalStatus;
	private String lifePartnerName;
	private Long lifePartnerId;
	private String phoneHome;
	private String phoneOffice;

	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public Date getMarriageAnniversary() {
		return marriageAnniversary;
	}
	public void setMarriageAnniversary(Date marriageAnniversary) {
		this.marriageAnniversary = marriageAnniversary;
	}
	public boolean isLifePartner() {
		return lifePartner;
	}
	public void setLifePartner(boolean lifePartner) {
		this.lifePartner = lifePartner;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
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
	public String getLifePartnerName() {
		return lifePartnerName;
	}
	public void setLifePartnerName(String lifePartnerName) {
		this.lifePartnerName = lifePartnerName;
	}
	public Long getLifePartnerId() {
		return lifePartnerId;
	}
	public void setLifePartnerId(Long lifePartnerId) {
		this.lifePartnerId = lifePartnerId;
	}
	public String getPhoneHome() {
		return phoneHome;
	}
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}
	public String getPhoneOffice() {
		return phoneOffice;
	}
	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

}
