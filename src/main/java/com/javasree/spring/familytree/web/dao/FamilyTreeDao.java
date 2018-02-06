package com.javasree.spring.familytree.web.dao;

import java.util.List;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeDao {
	
	public List<FamilyTree> findAll();
	
	public FamilyTree findFamilyTree(Long familyTreeId);
	
	public FamilyTree save(FamilyTree familyTree);

}
