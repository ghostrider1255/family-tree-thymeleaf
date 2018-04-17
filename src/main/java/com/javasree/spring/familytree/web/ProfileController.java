package com.javasree.spring.familytree.web;

import static com.javasree.spring.familytree.web.utils.URLConstant.PROFILE_VIEW;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.EDIT_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.DELETE_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_LIFE_PARTNER_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.INIT_LIFE_PARTNER_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.CUSTOME_PROFILE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dto.CustomeProfile;
import com.javasree.spring.familytree.web.dto.ResponseDto;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value=PROFILE_VIEW, method=RequestMethod.GET)
	public String viewProfile(@PathVariable("familyTreeId") String familyTreeId, Model model){
		
		Profile profile = new Profile();
		profile.setProfileId(Long.valueOf(0));
		model.addAttribute("profile", profile);
		return "profilePage";
	}
	
	@RequestMapping(value=SAVE_PROFILE, method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE , produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto saveProfile(@PathVariable("familyTreeId") String familyTreeId, @RequestBody Profile profile
			, BindingResult result,Model model){
		
		Profile newProfile = profileService.save(this.getValidatedProfile(profile));
		model.addAttribute("profile", newProfile);
		ResponseDto response = new ResponseDto();
		response.setMsg("success");
		response.setCode("200");
		Profile rootParentProfile = profileService.getRootPraent(newProfile);
		response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
		logger.debug("new member profile created with profile ID:"+newProfile.getProfileName());
		return response;
	}
	
	@RequestMapping(value = DELETE_PROFILE, method = RequestMethod.DELETE, 
			produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto deleteProfile(@PathVariable("familyTreeId") String familyTreeId, 
			@PathVariable("profileId") String profileId){
		
		ResponseDto response = new ResponseDto();
		if(profileId!=null && profileId.trim().length()>0){
			Optional<Profile> profileToBeDeleted = profileService.findProfile(Long.parseLong(profileId));
			if(profileToBeDeleted.isPresent()){
				Profile rootParentProfile = profileService.getRootPraent(profileToBeDeleted.get());
				profileService.delete(profileToBeDeleted.get().getProfileId());
				response.setMsg("success");
				response.setCode("200");
				response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
				logger.debug("deleting member with profile ID:{}",profileId);
			}
		}
		else{
			response.setCode("400");
			response.setMsg("error deleting member with profile ID:"+profileId);
			logger.debug("error deleting member with profile ID:{}",profileId);
		}
		return response;
	}
	
	@PostMapping(value = SAVE_LIFE_PARTNER_PROFILE , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveLifePartner(@PathVariable("familyTreeId") String familyTreeId,
			Profile lifePartner, BindingResult result, Model model){
		Optional<Profile> optioanlParentPartner = profileService.findProfile(lifePartner.getParentId());
		if(optioanlParentPartner.isPresent()){
			Profile parentPartner = optioanlParentPartner.get();
			lifePartner.setGender(this.getLifePartnerGender(parentPartner.getGender()));
			lifePartner.setMaritalStatus(parentPartner.getMaritalStatus());
			lifePartner.setMarriageAnniversary(parentPartner.getMarriageAnniversary());
			lifePartner.setLifePartner(true);
			profileService.save(lifePartner);
			
		}
		return "redirect:/familytree/"+ familyTreeId + "/view";
	}
	
	@GetMapping(value = "/getPartner/{parentProfileId}")
	public String getLifePartnerProfile(@PathVariable("parentProfileId") String parentProfileId,Model model){
		Profile lifePartner = null;
		lifePartner = new Profile();
		lifePartner.setParentId(Long.parseLong(parentProfileId));
		lifePartner.setLifePartner(true);
		model.addAttribute("lifePartner", lifePartner);
		return "familytree :: life-partner-form";
	}
	
	@GetMapping(value = EDIT_PROFILE, produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto editProfile(@PathVariable("familyTreeId") String familyTreeId, 
			@PathVariable("profileId") String profileId,Model model){
		ResponseDto response = new ResponseDto();
        Optional<Profile> profileToEdit = profileService.findProfile(Long.valueOf(profileId));
		if(profileToEdit.isPresent()){
			response.setMsg("success");
			response.setCode("200");
			response.setObject(profileToEdit.get());
			model.addAttribute("profile", profileToEdit.get());
		}
		return response;
	}
	
	@GetMapping(value = INIT_LIFE_PARTNER_PROFILE)
	public String setupLifePartner(@PathVariable("familyTreeId") String familyTreeId ,
			@PathVariable("profileId") String profileId, Model model){
		
		Profile lifePartner = null;
		lifePartner = new Profile();
		lifePartner.setParentId(Long.parseLong(profileId));
		lifePartner.setLifePartner(true);
		model.addAttribute("lifePartner", lifePartner);
		model.addAttribute("familyTreeId", familyTreeId);
		return "addLifePartnerForm";
	}
	
	@GetMapping(value = CUSTOME_PROFILE)
	public String getCustomeProfileView(@PathVariable("familyTreeId") String familyTreeId,
			@PathVariable("profileId") String profileId, Model model){
		if(profileId!=null && profileId.compareToIgnoreCase("null")!=0){
			Optional<Profile> optProfile = profileService.findProfile(Long.valueOf(profileId));
			if(optProfile.isPresent()){
				CustomeProfile custProf = profileService.getCustomeProfile(optProfile.get());
				
				Profile lifePartner = null;
				if(custProf.getLifePartnerId() == null){
					lifePartner = new Profile();
					lifePartner.setParentId(custProf.getCustomeProfileId());
					lifePartner.setLifePartner(true);
					model.addAttribute("lifePartner", lifePartner);
					
				}else{
					Optional<Profile> optLifePartner = profileService.findProfile(custProf.getLifePartnerId());
					if(optLifePartner.isPresent()){
						model.addAttribute("lifePartner", optLifePartner.get());
					}
				}
				model.addAttribute("customeProfile", custProf);
			}
		}
		else{
			model.addAttribute("customeProfile", new CustomeProfile());
		}
		return "customeProfileView::cust-profile-view";
	}
	
	private List<TreeNode> getTreeNodes(Long parentNodeId){
		List<TreeNode> nodes = new ArrayList<>();
		Optional<Profile> parentProfile = profileService.findProfile(parentNodeId);
		if(parentProfile.isPresent()){
			TreeNode parentNode = TreeUtils.getTreeNodeFromProfile(parentProfile.get());
			nodes.add(parentNode);
		}
		
		List<Profile> dependents = profileService.findDependents(parentNodeId);
		if(dependents != null && !dependents.isEmpty()){
			dependents.forEach( dependentProfile -> {
				if(profileService.isHavingDependents(dependentProfile.getProfileId())){
					nodes.addAll(getTreeNodes(dependentProfile.getProfileId()));
				}
				TreeNode node = TreeUtils.getTreeNodeFromProfile(dependentProfile);
				nodes.add(node);
			});
		}
		return nodes;
	}
	
	private Profile getValidatedProfile(Profile profile){
		if(profile!=null && !profile.isLifePartner()){
			Optional<Profile> optLifePartner = profileService.findLifePartner(profile.getProfileId());
			if(optLifePartner.isPresent()){
				updateCorrespondingPartner(profile, optLifePartner.get());
			}
		}
		else if(profile!=null && profile.isLifePartner()){
			Optional<Profile> optLifePartner = profileService.findProfile(profile.getParentId());
			if(optLifePartner.isPresent()){
				updateCorrespondingPartner(profile, optLifePartner.get());
			}
		}
		return profile ; 
	}
	
	private void updateCorrespondingPartner(Profile parentProfile, Profile lifePartnerProfile){

		lifePartnerProfile.setGender(getLifePartnerGender(parentProfile.getGender()));
		if(parentProfile.getDateOfDeath()!=null){
			lifePartnerProfile.setMaritalStatus("widow");
		}
		else{
			lifePartnerProfile.setMaritalStatus("married");
		}
		if(!parentProfile.isLifePartner()){
			lifePartnerProfile.setMarriageAnniversary(parentProfile.getMarriageAnniversary());
		}
		
		profileService.save(lifePartnerProfile);
	}
	
	private String getLifePartnerGender(String gender){
		if("MALE".equalsIgnoreCase(gender)){
			return "female";
		}
		else{
			return "male";
		}
	}
}
