package com.javasree.spring.familytree.web.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dto.CustomeEventCalendar;
import com.javasree.spring.familytree.web.dto.CustomeProfile;

public interface ProfileDao {

	public Profile save(Profile profile);
	
	public List<Profile> findAll();
	
	public List<Profile> findDependents(Long parentId);
	
	public List<Profile> findAllDependents(Long profileId);
	
	public Optional<Profile> findProfile(Long profileId);
	
	public Optional<Profile> findLifePartner(Long parentProfileId);
	
	public List<Profile> findChildren(Long parentProfileId);
	
	public void delete(Long profileId);
	
	public boolean isHavingDependents(Long parentId);
	
	public Profile getRootPraent(Profile currentProfile);
	
	public CustomeProfile getCustomeProfile(Profile profile);
	
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles,Map<Long,String> partnersMap);
}
