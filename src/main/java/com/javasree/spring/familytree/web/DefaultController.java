package com.javasree.spring.familytree.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.FamilyTreeService;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class DefaultController {

	private static final Logger log = LoggerFactory.getLogger(DefaultController.class);
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private FamilyTreeService familyTreeService;
	
	
	@GetMapping("/")
	public String defaultHome(){
		return "redirect:home";
	}
	
	@GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
	public String home(Model model){
		List<TreeNode> nodesList = null;
		nodesList = getNodes();
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			Map<String,String> menuMap = new HashMap<>();
			List<FamilyTree> treesList = familyTreeService.findAll();
			treesList.forEach( tree -> menuMap.put(String.valueOf(tree.getFamilyTreeId()), tree.getFamilyTreeName()));
			if(!menuMap.isEmpty()){
				model.addAttribute("menuItems", menuMap);
			}
			
			model.addAttribute("ft_items", jsonMapper.writeValueAsString(nodesList));
			model.addAttribute("profile", new Profile());
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage());
		}
		return "/home";
	}
	
	@GetMapping(value = "/createtreeform", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createNewTreeFamily(Model model){
		FamilyTree familyTree = new FamilyTree();
		model.addAttribute("familyTree", familyTree);
		return "/createtree";
	}
	
	@PostMapping(value = "/familytree/saveTree", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveFamilyTree(FamilyTree familyTree, Model model){
		FamilyTree newFamilyTree = familyTreeService.save(familyTree);
		return "redirect:/viewfulltree/"+newFamilyTree.getFamilyTreeId();
	}
	
	@GetMapping(value = "/viewfulltree/{familyTreeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTree(@PathVariable("familyTreeId") String familyTreeId, Model model){
		List<TreeNode> nodesList = null;
		FamilyTree currentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		nodesList = getTreeNodes(currentFamilyTree.getProfile().getProfileId());
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			Map<String,String> menuMap = new HashMap<>();
			List<FamilyTree> treesList = familyTreeService.findAll();
			treesList.forEach( tree -> menuMap.put(String.valueOf(tree.getFamilyTreeId()), tree.getFamilyTreeName()));
			if(!menuMap.isEmpty()){
				model.addAttribute("menuItems", menuMap);
			}
			
			model.addAttribute("ft_items", jsonMapper.writeValueAsString(nodesList));
			model.addAttribute("profile", currentFamilyTree.getProfile());
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage());
		}
		return "/viewfulltree";
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
