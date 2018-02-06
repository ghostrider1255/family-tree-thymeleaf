package com.javasree.spring.familytree.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.web.dao.FamilyTreeDao;
import com.javasree.spring.familytree.web.service.FamilyTreeService;

@Service
public class FamilyTreeServiceImpl implements FamilyTreeService{

	@Autowired
	private FamilyTreeDao familyTreeDao;
	
	@Override
	public List<FamilyTree> findAll() {
		return familyTreeDao.findAll();
	}

	@Override
	public FamilyTree save(FamilyTree familyTree) {
		return familyTreeDao.save(familyTree);
	}

	@Override
	public FamilyTree findFamilyTree(Long familyTreeId) {
		return familyTreeDao.findFamilyTree(familyTreeId);
	}

}
