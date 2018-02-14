package com.javasree.spring.familytree.web.dao;

import org.springframework.data.domain.Pageable;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeDao {
	
	public Iterable<FamilyTree> findAll();
	
	public Iterable<FamilyTree> findAll(Pageable pageble);
	
	public FamilyTree findFamilyTree(Long familyTreeId);
	
	public FamilyTree save(FamilyTree familyTree);

}
