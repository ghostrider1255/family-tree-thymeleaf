package com.javasree.spring.familytree.web.service;

import java.util.List;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeService {
	
	public List<FamilyTree> findAll();

	public FamilyTree findFamilyTree(Long familyTreeId);
	
	public FamilyTree save(FamilyTree familyTree);
}
