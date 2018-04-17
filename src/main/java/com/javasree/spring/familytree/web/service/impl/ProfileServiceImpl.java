package com.javasree.spring.familytree.web.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dao.impl.ProfileDaoImpl;
import com.javasree.spring.familytree.web.dto.CustomeEventCalendar;
import com.javasree.spring.familytree.web.dto.CustomeProfile;
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
	public List<Profile> findDependents(Long parentId) {
		return profileDao.findDependents(parentId);
	}

	@Override
	public boolean isHavingDependents(Long parentId) {
		return profileDao.isHavingDependents(parentId);
	}

	@Override
	public Optional<Profile> findProfile(Long profileId) {
		return profileDao.findProfile(profileId);
	}

	@Override
	public Profile getRootPraent(Profile currentProfile) {
		return profileDao.getRootPraent(currentProfile);
	}

	@Override
	public CustomeProfile getCustomeProfile(Profile profile) {
		return profileDao.getCustomeProfile(profile);
	}

	@Override
	public List<Profile> findAllDependents(Long profileId) {
		return profileDao.findAllDependents(profileId);
	}

	@Override
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles,Map<Long,String> partnersMap) {
		return profileDao.getCustomeEventCalender(profiles,partnersMap);
	}

	@Override
	public Optional<Profile> findLifePartner(Long parentProfileId) {
		return profileDao.findLifePartner(parentProfileId);
	}

	@Override
	public List<Profile> findChildren(Long parentProfileId) {
		return profileDao.findChildren(parentProfileId);
	}
}
