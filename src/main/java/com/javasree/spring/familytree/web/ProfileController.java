package com.javasree.spring.familytree.web;

import static com.javasree.spring.familytree.web.utils.URLConstant.VIEW_FAMILY_TREE;
import static com.javasree.spring.familytree.web.utils.URLConstant.PROFILE_VIEW;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_PROFILE;
import static com.javasree.spring.familytree.web.utils.URLConstant.DELETE_PROFILE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.ResponseDto;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class ProfileController {
	
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
	
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
	public ResponseDto saveProfile(@RequestBody Profile profile,Model model){
		Profile newProfile = profileService.save(profile);
		model.addAttribute("profile", newProfile);
		ResponseDto response = new ResponseDto();
		response.setMsg("success");
		response.setCode("200");
		Profile rootParentProfile = profileService.getPraent(newProfile);
		response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
		return response;
	}
	
	@RequestMapping(value = DELETE_PROFILE, method = RequestMethod.DELETE, 
			produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto deleteProfile(@PathVariable("profileId") String profileId){
		ResponseDto response = new ResponseDto();
		if(profileId!=null && profileId.trim().length()>0){
			Profile profileToBeDeleted = profileService.findProfile(Long.parseLong(profileId));
			Profile rootParentProfile = profileService.getPraent(profileToBeDeleted);
			profileService.delete(profileToBeDeleted.getProfileId());
			response.setMsg("success");
			response.setCode("200");
			response.setObject(getTreeNodes(rootParentProfile.getProfileId()));
		}
		else{
			response.setMsg("error");
			response.setCode("500");
		}
		return response;
	}
	
	@GetMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto getAllProfiles(Model model){
		ResponseDto response = new ResponseDto();
		response.setMsg("success");
		response.setCode("200");
		response.setObject(getNodes());
		return response;
	}
	@RequestMapping(value=VIEW_FAMILY_TREE, method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public String viewFamilyTree(Model model){
		List<TreeNode> nodesList = null;
		nodesList = getNodes();
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			model.addAttribute("items", jsonMapper.writeValueAsString(nodesList));
			model.addAttribute("profile", new Profile());
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return "familyTree";
	}
	
	private List<TreeNode> getTreeNodes(Long parentNodeId){
		List<TreeNode> nodes = new ArrayList<>();
		
		TreeNode parentNode = TreeUtils.getTreeNodeFromProfile(profileService.findProfile(parentNodeId));
		nodes.add(parentNode);
		
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
	
	
	public List<TreeNode> getNodes(){
		List<TreeNode> nodes = new ArrayList<>();
		
		List<Profile> profiles = profileService.findAll();
		if(profiles!=null && !profiles.isEmpty()){
			profiles.forEach( profile -> {
				TreeNode node = TreeUtils.getTreeNodeFromProfile(profile);
				nodes.add(node);
			});
		}
		else{
			Profile rootProfile = new Profile();
			rootProfile.setParentId(null);
			rootProfile.setProfileName("Profile full Name");
			rootProfile.setFirstName("First Name");
			rootProfile.setLastName("Last Name");
			rootProfile.setGender("male");
			Profile newRootProfile = profileService.save(rootProfile);
			
			TreeNode node = TreeUtils.getTreeNodeFromProfile(newRootProfile);
			nodes.add(node);
		}
		return nodes;
	}
}
