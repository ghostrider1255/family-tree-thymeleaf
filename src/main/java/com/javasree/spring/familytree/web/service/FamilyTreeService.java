package com.javasree.spring.familytree.web.service;

import org.springframework.data.domain.Pageable;

import com.javasree.spring.familytree.model.profile.FamilyTree;

public interface FamilyTreeService {
	
	public Iterable<FamilyTree> findAll();

	public Iterable<FamilyTree> findAll(Pageable pageble);
	
	public FamilyTree findFamilyTree(Long familyTreeId);
	
	public FamilyTree save(FamilyTree familyTree);
}
