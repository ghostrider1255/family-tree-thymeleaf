package com.javasree.spring.familytree.web.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javasree.spring.familytree.model.FamilyTree;

public interface FamilyTreeService {
	
	public Iterable<FamilyTree> findAll();

	public Iterable<FamilyTree> findAll(Pageable pageble);
	
	public Optional<FamilyTree> findFamilyTree(Long familyTreeId);
	
	public FamilyTree save(FamilyTree familyTree);
	
	public String exportTreeAsString(Long familyTreeId) throws JsonProcessingException;
	
}
