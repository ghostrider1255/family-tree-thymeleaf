package com.javasree.spring.familytree.web.dao;

import java.util.List;

import com.javasree.spring.familytree.model.profile.Profile;

public interface ProfileDao {

	public Profile save(Profile profile);
	
	public List<Profile> findAll();
	
	public Profile findProfile(Long profileId);
	
	public List<Profile> findByParentId(Long parentId);
	
	public void delete(Long profileId);
	
	public boolean existsByParentId(Long parentId);
	
	public Profile getPraent(Profile currentProfile);
}
