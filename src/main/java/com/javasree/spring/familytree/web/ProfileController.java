package com.javasree.spring.familytree.web;

import static com.javasree.spring.familytree.web.utils.URLConstant.PROFILE_VIEW;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.DELETE_PROFILE;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javasree.spring.familytree.model.profile.Profile;
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
	public String viewProfile(Model model){
		Profile profile = new Profile();
		profile.setProfileId(Long.valueOf(0));
		model.addAttribute("profile", profile);
		return "profilePage";
	}
	
	@RequestMapping(value=SAVE_PROFILE, method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE , produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto saveProfile(@RequestBody Profile profile, BindingResult result,Model model){
		Profile newProfile = profileService.save(profile);
		model.addAttribute("profile", newProfile);
		ResponseDto response = new ResponseDto();
		response.setMsg("success");
		response.setCode("200");
		Profile rootParentProfile = profileService.getPraent(newProfile);
		response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
		logger.debug("new member profile created with profile ID:"+newProfile.getProfileName());
		return response;
	}
	
	@RequestMapping(value = DELETE_PROFILE, method = RequestMethod.DELETE, 
			produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto deleteProfile(@PathVariable("profileId") String profileId){
		ResponseDto response = new ResponseDto();
		if(profileId!=null && profileId.trim().length()>0){
			Optional<Profile> profileToBeDeleted = profileService.findProfile(Long.parseLong(profileId));
			if(profileToBeDeleted.isPresent()){
				Profile rootParentProfile = profileService.getPraent(profileToBeDeleted.get());
				profileService.delete(profileToBeDeleted.get().getProfileId());
				response.setMsg("success");
				response.setCode("200");
				response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
				logger.debug("deleting member with profile ID:"+profileId);
			}
		}
		else{
			response.setCode("400");
			response.setMsg("error deleting member with profile ID:"+profileId);
			logger.debug("error deleting member with profile ID:"+profileId);
		}
		return response;
	}
	
	private List<TreeNode> getTreeNodes(Long parentNodeId){
		List<TreeNode> nodes = new ArrayList<>();
		Optional<Profile> parentProfile = profileService.findProfile(parentNodeId);
		if(parentProfile.isPresent()){
			TreeNode parentNode = TreeUtils.getTreeNodeFromProfile(parentProfile.get());
			nodes.add(parentNode);
		}
		
		List<Profile> children = profileService.findByParentId(parentNodeId);
		if(children != null && !children.isEmpty()){
			children.forEach( childProfile -> {
				if(profileService.existsByParentId(childProfile.getProfileId())){
					nodes.addAll(getTreeNodes(childProfile.getProfileId()));
				}
				TreeNode node = TreeUtils.getTreeNodeFromProfile(childProfile);
				nodes.add(node);
			});
		}
		return nodes;
	}
}
