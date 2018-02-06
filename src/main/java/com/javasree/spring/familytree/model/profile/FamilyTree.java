package com.javasree.spring.familytree.model.profile;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="FAMILYTREE")
public class FamilyTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8292835775278224132L;
	
	@Id
	@Column(name="familyTreeId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long familyTreeId;
	private String familyTreeName;
	@OneToOne(cascade = CascadeType.ALL)
	private Profile profile;
	
	public Long getFamilyTreeId() {
		return familyTreeId;
	}
	public void setFamilyTreeId(Long familyTreeId) {
		this.familyTreeId = familyTreeId;
	}
	public String getFamilyTreeName() {
		return familyTreeName;
	}
	public void setFamilyTreeName(String familyTreeName) {
		this.familyTreeName = familyTreeName;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
