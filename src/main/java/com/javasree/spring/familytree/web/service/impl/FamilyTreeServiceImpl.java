package com.javasree.spring.familytree.web.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.web.dao.FamilyTreeDao;
import com.javasree.spring.familytree.web.service.FamilyTreeService;

@Service
public class FamilyTreeServiceImpl implements FamilyTreeService{

	@Autowired
	private FamilyTreeDao familyTreeDao;
	
	@Override
	public Iterable<FamilyTree> findAll() {
		return familyTreeDao.findAll();
	}

	@Override
	public FamilyTree save(FamilyTree familyTree) {
		return familyTreeDao.save(familyTree);
	}

	@Override
	public Optional<FamilyTree> findFamilyTree(Long familyTreeId) {
		return familyTreeDao.findFamilyTree(familyTreeId);
	}

	@Override
	public Iterable<FamilyTree> findAll(Pageable pageble) {
		return familyTreeDao.findAll(pageble);
	}

}
