package com.javasree.spring.familytree.web.service;

import java.util.List;

import com.javasree.spring.familytree.model.CustomeEventCalendar;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.Profile;

public interface ProfileService {
	
	public Profile save(Profile profile);
	
	public List<Profile> findAll();
	
	public List<Profile> findAllChildren(Long profileId);
	
	public Profile findProfile(Long profileId);
	
	public List<Profile> findByParentId(Long parentId);
	
	public void delete(Long profileId);
	
	public Profile getPraent(Profile currentProfile);
	
	public boolean existsByParentId(Long parentId);
	
	public CustomeProfile getCustomeProfile(Profile profile);
	
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles);
}
