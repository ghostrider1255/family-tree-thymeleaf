package com.javasree.spring.familytree.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dao.ProfileDao;
import com.javasree.spring.familytree.web.jpa.ProfileRepository;

@Service
public class ProfileDaoImpl implements ProfileDao{

	@Autowired
	private ProfileRepository profileRepository;
	
	public ProfileDaoImpl(){
		super();
	}
	
	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public List<Profile> findAll() {
		return profileRepository.findAll();
	}

	@Override
	public void delete(Long profileId){
		List<Profile> children = this.findByParentId(profileId);
		for(Profile profile: children){
			if(profileRepository.existsByParentId(profile.getProfileId())){
				this.delete(profile.getProfileId());
			}
			else{
				profileRepository.delete(profile.getProfileId());
			}
		}
		profileRepository.delete(profileId);
	}

	@Override
	public List<Profile> findByParentId(Long parentId) {
		return profileRepository.findByParentId(parentId);
	}

	@Override
	public boolean existsByParentId(Long parentId) {
		return profileRepository.existsByParentId(parentId);
	}

	@Override
	public Profile findProfile(Long profileId) {
		return profileRepository.findOne(profileId);
	}

	@Override
	public Profile getPraent(Profile currentProfile) {
		Profile parentProfile = null;
		if(currentProfile!=null && currentProfile.getParentId()!=null){
			Profile currentParentProfile = profileRepository.getOne(currentProfile.getParentId());
			if(currentParentProfile!=null){
				parentProfile = this.getPraent(currentParentProfile);
			}
		}
		else{
			parentProfile = currentProfile;
		}
		if(parentProfile!=null && parentProfile.getParentId()==null){
			return parentProfile;
		}
		return parentProfile;
	}
}
