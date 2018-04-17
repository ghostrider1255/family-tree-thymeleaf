package com.javasree.spring.familytree.web.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.FamilyTree;
import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dao.FamilyTreeDao;
import com.javasree.spring.familytree.web.dto.FamilyTreeJSON;
import com.javasree.spring.familytree.web.jpa.FamilyTreeRepository;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Service
public class FamilyTreeDaoImpl implements FamilyTreeDao{

	@Autowired
	private FamilyTreeRepository familyTreeRepository;
	
	@Autowired
	private ProfileService profileService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
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
	public Optional<FamilyTree> findFamilyTree(Long familyTreeId) {
		return familyTreeRepository.findById(familyTreeId);
	}

	@Override
	public String exportTreeAsString(Long familyTreeId) throws JsonProcessingException {
		FamilyTreeJSON familyTreeAsJSON = new FamilyTreeJSON();
		Optional<FamilyTree> optFamilyTree = familyTreeRepository.findById(familyTreeId);
		if(optFamilyTree.isPresent()){
			familyTreeAsJSON.setFamilyTree(optFamilyTree.get());
			List<Profile> profiles = profileService.findAllDependents(optFamilyTree.get().getProfile().getProfileId());
			if(profiles!=null && !profiles.isEmpty()){
				familyTreeAsJSON.setProfiles(profiles);
			}
		}
		return TreeUtils.objectToJsonString(familyTreeAsJSON); 
	}
}
