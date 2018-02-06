package com.javasree.spring.familytree.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dao.impl.ProfileDaoImpl;
import com.javasree.spring.familytree.web.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
	private ProfileDaoImpl profileDao;

	@Override
	public Profile save(Profile profile) {
		return profileDao.save(profile);
	}

	@Override
	public List<Profile> findAll() {
		return profileDao.findAll();
	}

	@Override
	public void delete(Long profileId){
		profileDao.delete(profileId);
	}

	@Override
	public List<Profile> findByParentId(Long parentId) {
		return profileDao.findByParentId(parentId);
	}

	@Override
	public boolean existsByParentId(Long parentId) {
		return profileDao.existsByParentId(parentId);
	}

	@Override
	public Profile findProfile(Long profileId) {
		return profileDao.findProfile(profileId);
	}

	@Override
	public Profile getPraent(Profile currentProfile) {
		return profileDao.getPraent(currentProfile);
	}
	
	
}
