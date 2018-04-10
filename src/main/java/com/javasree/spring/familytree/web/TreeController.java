package com.javasree.spring.familytree.web;


import static com.javasree.spring.familytree.web.utils.URLConstant.CREATE_TREE_FORM;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_TREE;
import static com.javasree.spring.familytree.web.utils.URLConstant.VIEW_TREE;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.FamilyTreeService;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class TreeController {

	private static final Logger logger = LoggerFactory.getLogger(TreeController.class);
	
	@Autowired
	private FamilyTreeService familyTreeService;
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping(value = CREATE_TREE_FORM , produces = MediaType.APPLICATION_JSON_VALUE)
	public String createNewTreeFamily(Model model){
		model.addAttribute("familyTree", new FamilyTree());
		return "/createTree";
	}
	
	@PostMapping(value = SAVE_TREE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveFamilyTree(FamilyTree familyTree, BindingResult result, Model model){
		FamilyTree newFamilyTree = familyTreeService.save(familyTree);
		return "redirect:/familytree/"+ newFamilyTree.getFamilyTreeId() + "/view";
	}
	
	@GetMapping(value = VIEW_TREE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTree(@PathVariable("familyTreeId") String familyTreeId, Model model){
		List<TreeNode> nodesList = null;
		Optional<FamilyTree> optCurrentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		if(optCurrentFamilyTree.isPresent()){
			FamilyTree currentFamilyTree = optCurrentFamilyTree.get();
			nodesList = getTreeNodes(currentFamilyTree.getProfile().getProfileId());
			ObjectMapper jsonMapper = new ObjectMapper();
			try {
				model.addAttribute("familytree", currentFamilyTree);
				model.addAttribute("ft_items", jsonMapper.writeValueAsString(nodesList));
				model.addAttribute("profile", new Profile());
				
				CustomeProfile customeProfile = profileService.getCustomeProfile(currentFamilyTree.getProfile());
				model.addAttribute("customeProfile", customeProfile);
				
			} catch (JsonProcessingException e) {
				logger.warn(e.getMessage());
			}
		}
		return "/viewfulltree";
	}
	
	private List<TreeNode> getTreeNodes(Long parentNodeId){
		List<TreeNode> nodes = new ArrayList<>();
		Optional<Profile> optParentProfile = profileService.findProfile(parentNodeId);
		if(optParentProfile.isPresent()){
			
			TreeNode parentNode = TreeUtils.getTreeNodeFromProfile(optParentProfile.get());
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
		}
		return nodes;
	}
}
