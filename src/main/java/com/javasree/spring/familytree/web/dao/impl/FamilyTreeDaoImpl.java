package com.javasree.spring.familytree.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.web.dao.FamilyTreeDao;
import com.javasree.spring.familytree.web.jpa.FamilyTreeRepository;

@Service
public class FamilyTreeDaoImpl implements FamilyTreeDao{

	@Autowired
	private FamilyTreeRepository familyTreeRepository;
	
	public FamilyTreeDaoImpl(){
		super();
	}
	
	@Override
	public Iterable<FamilyTree> findAll() {
		return familyTreeRepository.findAll();
	}
	
	public Iterable<FamilyTree> findAll(Pageable pageble){
		return familyTreeRepository.findAll(pageble);
	}

	@Override
	public FamilyTree save(FamilyTree familyTree) {
		return familyTreeRepository.save(familyTree);
	}

	@Override
	public FamilyTree findFamilyTree(Long familyTreeId) {
		return familyTreeRepository.findOne(familyTreeId);
	}

}
