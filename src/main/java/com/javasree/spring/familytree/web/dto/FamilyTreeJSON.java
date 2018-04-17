package com.javasree.spring.familytree.web.dto;

import java.util.List;

import com.javasree.spring.familytree.model.FamilyTree;
import com.javasree.spring.familytree.model.Profile;

public class FamilyTreeJSON {

	private FamilyTree familyTree;
	private List<Profile> profiles;
	
	public FamilyTree getFamilyTree() {
		return familyTree;
	}
	public void setFamilyTree(FamilyTree familyTree) {
		this.familyTree = familyTree;
	}
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}
