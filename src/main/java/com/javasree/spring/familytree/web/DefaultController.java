package com.javasree.spring.familytree.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.CustomeEventCalendar;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.PagerDto;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.FamilyTreeService;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class DefaultController {

	private static final Logger log = LoggerFactory.getLogger(DefaultController.class);
	
	private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10};
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private FamilyTreeService familyTreeService;
	
	
	@GetMapping("/")
	public String defaultHome(){
		return "redirect:home";
	}
	
	@GetMapping(value = "/customeProfileView/{profileId}")
	public String getCustomeProfileView(@PathVariable("profileId") String profileId, Model model){
		if(profileId!=null && profileId.compareToIgnoreCase("null")!=0){
			Profile profile = profileService.findProfile(Long.valueOf(profileId));
			CustomeProfile custProf = profileService.getCustomeProfile(profile);
			model.addAttribute("customeProfile", custProf);
		}
		else{
			model.addAttribute("customeProfile", new CustomeProfile());
		}
		return "customeProfileView::cust-profile-view";
	}
	
	@GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
	public String home(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,  Model model){

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() -1;
		Page<FamilyTree> treePages = (Page<FamilyTree>) familyTreeService.findAll(new PageRequest(evalPage, evalPageSize));
		PagerDto pager = new PagerDto(treePages.getTotalPages(), treePages.getNumber(), BUTTONS_TO_SHOW);
		model.addAttribute("treesList", treePages);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSize", PAGE_SIZES);
		model.addAttribute("pager", pager);
		return "/home";
	}
	
	@GetMapping(value = "/createtreeform", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createNewTreeFamily(Model model){
		model.addAttribute("familyTree", new FamilyTree());
		return "/createTree";
	}
	
	@PostMapping(value = "/familytree/saveTree", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveFamilyTree(FamilyTree familyTree, BindingResult result, Model model){
		FamilyTree newFamilyTree = familyTreeService.save(familyTree);
		return "redirect:/viewfulltree/"+newFamilyTree.getFamilyTreeId();
	}
	
	@GetMapping(value = "/events/{familyTreeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String events(@PathVariable("familyTreeId") String familyTreeId, Model model){
		FamilyTree currentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		List<Profile> profilesForEvents = profileService.findAllChildren(currentFamilyTree.getProfile().getProfileId());
		CustomeEventCalendar events= profileService.getCustomeEventCalender(profilesForEvents);
		model.addAttribute("eventsCalender", events);
		model.addAttribute("familytree", currentFamilyTree);
		return "/events";
	}
	
	
	@GetMapping(value = "/viewfulltree/{familyTreeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTree(@PathVariable("familyTreeId") String familyTreeId, Model model){
		List<TreeNode> nodesList = null;
		FamilyTree currentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		nodesList = getTreeNodes(currentFamilyTree.getProfile().getProfileId());
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			model.addAttribute("familytree", currentFamilyTree);
			model.addAttribute("ft_items", jsonMapper.writeValueAsString(nodesList));
			model.addAttribute("profile", new Profile());
			model.addAttribute("customeProfile", profileService.getCustomeProfile(currentFamilyTree.getProfile()));
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage());
		}
		return "/viewfulltree";
	}
	@GetMapping(value = "/aboutus", produces = MediaType.APPLICATION_JSON_VALUE)
	public String aboutUs(Model model){
		return "/aboutus";
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
}
