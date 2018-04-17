package com.javasree.spring.familytree.web.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dao.ProfileDao;
import com.javasree.spring.familytree.web.dto.CustomeEventCalendar;
import com.javasree.spring.familytree.web.dto.CustomeProfile;
import com.javasree.spring.familytree.web.dto.Event;
import com.javasree.spring.familytree.web.jpa.ProfileRepository;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Service
public class ProfileDaoImpl implements ProfileDao{

	private static final Logger logger = LoggerFactory.getLogger(ProfileDaoImpl.class);
	
	@Autowired
	private ProfileRepository profileRepository;
	
	private static final String STANDARD_UTILS_DATE_FORMAT="yyyyMMdd";
	private static final String EVENTS_CALENDER_DATE_FORMAT="yyyy-MM-dd";
	private static final int EVENTS_CALENDER_MONTH_RANGE=1;
	
	public ProfileDaoImpl(){
		super();
	}
	
	/**
	 * method to save the 'Profile' object into the data base.
	 * @param: Profile object
	 */
	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}
	
	/**
	 * method that returns all the profile present in the data base irrespective of the Tree.
	 */

	@Override
	public List<Profile> findAll() {
		return profileRepository.findAll();
	}

	/**
	 * method that delete's the profile and all it's dependent(children and life partner) profiles.
	 * @param: Long profileId
	 */
	@Override
	public void delete(Long profileId){
		List<Profile> dependents = this.findDependents(profileId);
		for(Profile profile: dependents){
			if(profileRepository.existsByParentId(profile.getProfileId())){
				this.delete(profile.getProfileId());
			}
			else{
				profileRepository.deleteById(profile.getProfileId());
			}
		}
		profileRepository.deleteById(profileId);
	}
	
	/** 
	 * finds all the dependents for a profile and returns a list of dependents which has both Life Partner and immediate children(not grand children)
	 * @param Long 'parentId' parameter which represents the parent Id of the dependents.
	 * @return List of all profile which belongs to the same Parent Profile.
	 */
	
	@Override
	public List<Profile> findDependents(Long parentId) {
		return profileRepository.findByParentId(parentId);
	}

	/**
	 * method that takes a Long parameter that represent the 'parentId' and returns 'Profile' object if
	 * the corresponding 'LifePartner' exists.
	 * @param Long: parentId
	 * @return 'Profile' object of the LifePartner record.
	 */
	@Override
	public Optional<Profile> findLifePartner(Long parentProfileId) {
		List<Profile> profiles = profileRepository.findByParentIdAndLifePartner(parentProfileId, true);
		if(profiles!=null && !profiles.isEmpty()){
			Optional<Profile> optLifePartner = Optional.of(profiles.get(0));
			return optLifePartner;
		}
		else{
			return Optional.empty();
		}
	}

	@Override
	public List<Profile> findChildren(Long parentProfileId) {
		return profileRepository.findByParentIdAndLifePartner(parentProfileId, false);
	}
	
	
	@Override
	public boolean isHavingDependents(Long parentId) {
		return profileRepository.existsByParentId(parentId);
	}

	@Override
	public Optional<Profile> findProfile(Long profileId) {
		return profileRepository.findById(profileId);
	}

	@Override
	public List<Profile> findAllDependents(Long profileId){
		List<Profile> childrenList = new ArrayList<>();
		if(profileId != null){
			Optional<Profile> currentProfile = this.findProfile(profileId);
			List<Profile> dependents = this.findDependents(profileId);
			for(Profile dependent: dependents){
				if(profileRepository.existsByParentId(dependent.getProfileId())){
					childrenList.addAll(findAllDependents(dependent.getProfileId()));
				}
				else{
					childrenList.add(dependent);
				}
			}
			if(currentProfile.isPresent())
			childrenList.add(currentProfile.get());
		}
		return childrenList;
	}
	
	
	@Override
	public Profile getRootPraent(Profile currentProfile) {
		Profile parentProfile = null;
		if(currentProfile!=null && currentProfile.getParentId()!=null){
			Profile currentParentProfile = profileRepository.getOne(currentProfile.getParentId());
			if(currentParentProfile!=null){
				parentProfile = this.getRootPraent(currentParentProfile);
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
		customeProfile.setCustomeProfileId(profile.getProfileId());
		customeProfile.setProfileName(profile.getProfileName());
		customeProfile.setFirstName(profile.getFirstName());
		customeProfile.setLastName(profile.getLastName());
		customeProfile.setDateOfBirth(profile.getDateOfBirth());
		customeProfile.setGender(profile.getGender());
		customeProfile.setAge(Long.valueOf(TreeUtils.getAge(profile.getDateOfBirth())));
		customeProfile.setPhoneHome(profile.getPhonePersonal());
		customeProfile.setPhoneOffice(profile.getPhoneOffice());
		
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
			Date minDate = TreeUtils.rangeDate(currentMonthStartDate, STANDARD_UTILS_DATE_FORMAT, -EVENTS_CALENDER_MONTH_RANGE);
			String minDateString = TreeUtils.convertDateToString(minDate, STANDARD_UTILS_DATE_FORMAT);
			String currentMonthEndDate = TreeUtils.computeEndDate(currentMonthStartDate);
			Date maxDate = TreeUtils.rangeDate(currentMonthEndDate, STANDARD_UTILS_DATE_FORMAT, EVENTS_CALENDER_MONTH_RANGE);//make current month end date as the maxdate
			String maxDateString = TreeUtils.convertDateToString(maxDate, STANDARD_UTILS_DATE_FORMAT);
			
			eventsCalender.setDefaultDate(TreeUtils.convertDateFormat(todayDateAsString, STANDARD_UTILS_DATE_FORMAT, EVENTS_CALENDER_DATE_FORMAT));
			eventsCalender.setMinDate(TreeUtils.convertDateFormat(minDateString, STANDARD_UTILS_DATE_FORMAT, EVENTS_CALENDER_DATE_FORMAT));
			eventsCalender.setMaxDate(TreeUtils.convertDateFormat(maxDateString, STANDARD_UTILS_DATE_FORMAT, EVENTS_CALENDER_DATE_FORMAT));
			List<Event> events = new ArrayList<>();
			for (Profile profile : profiles) {
				
				Event event = getIfEvent(profile.getDateOfBirth());
				if(event !=null){
					event.setNote(profile.getProfileName() +"'s BIRTHDAY");
					events.add(event);
				}
				event = getIfEvent(profile.getDateOfDeath());
				if(event !=null){
					event.setNote(profile.getProfileName() +"'s DEATH annivesary");
					events.add(event);
				}
				if(!profile.isLifePartner()){
					event = getIfEvent(profile.getMarriageAnniversary());
					if(event !=null){
						String lifePartnersName = partnersMap.get(profile.getProfileId());
						String eventNoteMessage = lifePartnersName!=null ?  (profile.getProfileName() +" &" + lifePartnersName +"'s MARRIAGE annivesary") : 
							(profile.getProfileName() +"'s MARRIAGE annivesary");
						event.setNote(eventNoteMessage);
						events.add(event);
					}
				}
			}
			Collections.sort(events,(Event eventOne,Event eventTwo) -> {
				String eventOneDate = eventOne.getEventDate().toString();
				String eventTwoDate = eventTwo.getEventDate().toString();
				return eventOneDate.compareTo(eventTwoDate);
			});
			eventsCalender.setEvents(events);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return eventsCalender;
	}
	
	private Event getIfEvent(Date eventDate){
		Event birthDayEvent = null;
		if(eventDate !=null && TreeUtils.isEvent((Date)eventDate.clone())){
			birthDayEvent = new Event();
			birthDayEvent.setEventDate(TreeUtils.getEvent(eventDate));
		}
		return birthDayEvent;
	}

}
