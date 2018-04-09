package com.javasree.spring.familytree.web.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javasree.spring.familytree.model.CustomeEventCalendar;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.Profile;

public interface ProfileDao {

	public Profile save(Profile profile);
	
	public List<Profile> findAll();
	
	public List<Profile> findAllChildren(Long profileId);
	
	public Optional<Profile> findProfile(Long profileId);
	
	public List<Profile> findByParentId(Long parentId);
	
	public void delete(Long profileId);
	
	public boolean existsByParentId(Long parentId);
	
	public Profile getPraent(Profile currentProfile);
	
	public CustomeProfile getCustomeProfile(Profile profile);
	
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles,Map<Long,String> partnersMap);
}
