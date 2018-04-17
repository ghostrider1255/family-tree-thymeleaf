package com.javasree.spring.familytree.web.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dto.CustomeEventCalendar;
import com.javasree.spring.familytree.web.dto.CustomeProfile;

public interface ProfileService {
	
	public Profile save(Profile profile);
	
	public List<Profile> findAll();
	
	public List<Profile> findAllDependents(Long profileId);
	
	public Optional<Profile> findProfile(Long profileId);
	
	public List<Profile> findDependents(Long parentId);
	
	public Optional<Profile> findLifePartner(Long parentProfileId);
	
	public List<Profile> findChildren(Long parentProfileId);
	
	public void delete(Long profileId);
	
	public Profile getRootPraent(Profile currentProfile);
	
	public boolean isHavingDependents(Long parentId);
	
	public CustomeProfile getCustomeProfile(Profile profile);
	
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles,Map<Long,String> partnersMap);
}
