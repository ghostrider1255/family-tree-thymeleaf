package com.javasree.spring.familytree.model.profile;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.javasree.spring.familytree.web.utils.CustomeDateDeserializer;

@Entity
@Table(name="profile")
public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7441252273092957808L;
	@Id
	@Column(name="profileId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long profileId;
	private String profileName;
	private String firstName;
	private String lastName;
	private String gender;
	@JsonDeserialize(using = CustomeDateDeserializer.class)
	@DateTimeFormat(pattern="dd-mm-yyyy")
	private Date dateOfBirth;
	@JsonDeserialize(using = CustomeDateDeserializer.class)
	@DateTimeFormat(pattern="dd-mm-yyyy")
	private Date dateOfDeath;
	@JsonDeserialize(using = CustomeDateDeserializer.class)
	@DateTimeFormat(pattern="dd-mm-yyyy")
	private Date marriageAnniversary;
	private String maritalStatus;
	private boolean lifePartner;
	private Long parentId;
	
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
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
	public Date getMarriageAnniversary() {
		return marriageAnniversary;
	}
	public void setMarriageAnniversary(Date marriageAnniversary) {
		this.marriageAnniversary = marriageAnniversary;
	}
	public Date getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((profileId == null) ? 0 : profileId.hashCode());
		result = prime * result + ((profileName == null) ? 0 : profileName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		} else if (!profileId.equals(other.profileId))
			return false;
		if (profileName == null) {
			if (other.profileName != null)
				return false;
		} else if (!profileName.equals(other.profileName))
			return false;
		return true;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public boolean isLifePartner() {
		return lifePartner;
	}
	public void setLifePartner(boolean lifePartner) {
		this.lifePartner = lifePartner;
	}
	/*@Override
	public String toString(){
		return this.toString();
	}*/
}
