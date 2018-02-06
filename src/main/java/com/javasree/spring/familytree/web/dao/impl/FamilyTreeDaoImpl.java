package com.javasree.spring.familytree.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<FamilyTree> findAll() {
		return familyTreeRepository.findAll();
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
