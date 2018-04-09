package com.javasree.spring.familytree.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.CustomeEventCalendar;
import com.javasree.spring.familytree.model.Event;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dao.ProfileDao;
import com.javasree.spring.familytree.web.jpa.ProfileRepository;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Service
public class ProfileDaoImpl implements ProfileDao{

	private static final Logger logger = LoggerFactory.getLogger(ProfileDaoImpl.class);
	@Autowired
	private ProfileRepository profileRepository;
	
	private static final String STANDARD_UTILS_DATE_FORMAT="yyyyMMdd";
	
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
				profileRepository.deleteById(profile.getProfileId());
			}
		}
		profileRepository.deleteById(profileId);
	}

	public List<Profile> findChildrentByParentId(Long parentId) {
		return profileRepository.findByParentIdAndLifePartner(parentId, true);
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
	public Optional<Profile> findProfile(Long profileId) {
		return profileRepository.findById(profileId);
	}

	@Override
	public List<Profile> findAllChildren(Long profileId){
		List<Profile> childrenList = new ArrayList<>();
		if(profileId != null){
			Optional<Profile> currentProfile = this.findProfile(profileId);
			List<Profile> children = this.findByParentId(profileId);
			for(Profile child: children){
				if(profileRepository.existsByParentId(child.getProfileId())){
					childrenList.addAll(findAllChildren(child.getProfileId()));
				}
				else{
					childrenList.add(child);
				}
			}
			if(currentProfile.isPresent())
			childrenList.add(currentProfile.get());
		}
		return childrenList;
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
	
	@Override
	public CustomeProfile getCustomeProfile(Profile profile){
		CustomeProfile customeProfile = new CustomeProfile();
		customeProfile.setProfileId(profile.getProfileId());
		customeProfile.setProfileName(profile.getProfileName());
		customeProfile.setFirstName(profile.getFirstName());
		customeProfile.setLastName(profile.getLastName());
		customeProfile.setDateOfBirth(profile.getDateOfBirth());
		customeProfile.setGender(profile.getGender());
		customeProfile.setAge(Long.valueOf(TreeUtils.getAge(profile.getDateOfBirth())));
		
		customeProfile.setMaritalStatus(profile.getMaritalStatus().toUpperCase());
		if(!profile.getMaritalStatus().equalsIgnoreCase("single")){
			if(profile.getMarriageAnniversary()!=null){
				customeProfile.setMarriageAnniversary(profile.getMarriageAnniversary());
				customeProfile.setNumberOfCelebratedAnniversaries(Long.valueOf(TreeUtils.getAge(profile.getMarriageAnniversary())));
				Profile lifePartnerProfile = profileRepository.getLifePartner(profile.getProfileId());
				if(lifePartnerProfile!=null){
					customeProfile.setLifePartnerName(lifePartnerProfile.getProfileName());
					customeProfile.setLifePartnerId(lifePartnerProfile.getProfileId());
				}
			}
			 if(profile.isLifePartner()){
				 customeProfile.setLifePartner(profile.isLifePartner());
				 List<Profile> childrenList = profileRepository.findByParentIdAndLifePartner(profile.getParentId(),false);
				 customeProfile.setNumberOfChildren(Long.valueOf(childrenList.size()));
			}
			 else{
				    List<Profile> childrenList = profileRepository.findByParentIdAndLifePartner(profile.getProfileId(),false);
					customeProfile.setNumberOfChildren(Long.valueOf(childrenList.size()));
			 }
		}
		
		if(profile.getParentId()!=null){
			Optional<Profile> parent = profileRepository.findById(profile.getParentId());
			if(parent.isPresent()){
				customeProfile.setChildOf(parent.get().getProfileName());
			}
		}
		return customeProfile;
	}

	@Override
	public CustomeEventCalendar getCustomeEventCalender(List<Profile> profiles,Map<Long,String> partnersMap) {
		CustomeEventCalendar eventsCalender = new CustomeEventCalendar();
		Date todayDate = new Date();
		
		try {
			String todayDateAsString = TreeUtils.convertDateToString(todayDate, STANDARD_UTILS_DATE_FORMAT); //default date for the event calender
			String currentMonthStartDate = TreeUtils.computeMonthStartDate(todayDateAsString); //make current months 1st as the min date
			Date minDate = TreeUtils.rangeDate(currentMonthStartDate, STANDARD_UTILS_DATE_FORMAT, -1);
			String minDateString = TreeUtils.convertDateToString(minDate, STANDARD_UTILS_DATE_FORMAT);
			String currentMonthEndDate = TreeUtils.computeEndDate(currentMonthStartDate);
			Date maxDate = TreeUtils.rangeDate(currentMonthEndDate, STANDARD_UTILS_DATE_FORMAT, 1);//make current month end date as the maxdate
			String maxDateString = TreeUtils.convertDateToString(maxDate, STANDARD_UTILS_DATE_FORMAT);
			
			eventsCalender.setDefaultDate(TreeUtils.convertDateFormat(todayDateAsString, STANDARD_UTILS_DATE_FORMAT, "yyyy-MM-dd"));
			eventsCalender.setMinDate(TreeUtils.convertDateFormat(minDateString, STANDARD_UTILS_DATE_FORMAT, "yyyy-MM-dd"));
			eventsCalender.setMaxDate(TreeUtils.convertDateFormat(maxDateString, STANDARD_UTILS_DATE_FORMAT, "yyyy-MM-dd"));
			List<Event> events = new ArrayList<>();
			for (Profile profile : profiles) {
				
				Event event = getIfEvent(profile.getDateOfBirth(), profile.getProfileName());
				if(event !=null){
					event.setNote(profile.getProfileName() +"'s BIRTHDAY");
					events.add(event);
				}
				event = getIfEvent(profile.getDateOfDeath(), profile.getProfileName());
				if(event !=null){
					event.setNote(profile.getProfileName() +"'s DEATH annivesary");
					events.add(event);
				}
				if(!profile.isLifePartner()){
					event = getIfEvent(profile.getMarriageAnniversary(), profile.getProfileName());
					if(event !=null){
						String lifePartnersName = partnersMap.get(profile.getProfileId());
						event.setNote(profile.getProfileName() +" &" + lifePartnersName +"'s MARRIAGE annivesary");
						events.add(event);
					}
				}
			}
			eventsCalender.setEvents(events);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return eventsCalender;
	}
	
	private Event getIfEvent(Date eventDate, String personName){
		Event birthDayEvent = null;
		if(eventDate !=null && TreeUtils.isEvent((Date)eventDate.clone())){
			birthDayEvent = new Event();
			birthDayEvent.setEventDate(TreeUtils.getEvent(eventDate));
			//birthDayEvent.setNote(personName +" s BIRTHDAY");
		}
		return birthDayEvent;
	}
}
