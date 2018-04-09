package com.javasree.spring.familytree.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.CustomeEventCalendar;
import com.javasree.spring.familytree.model.profile.CustomeProfile;
import com.javasree.spring.familytree.model.profile.FamilyTree;
import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.PagerDto;
import com.javasree.spring.familytree.web.dto.ResponseDto;
import com.javasree.spring.familytree.web.dto.TreeNode;
import com.javasree.spring.familytree.web.service.FamilyTreeService;
import com.javasree.spring.familytree.web.service.ProfileService;
import com.javasree.spring.familytree.web.utils.TreeUtils;

@Controller
public class DefaultController {

	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
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
		return "redirect:/home";
	}
	
	@PostMapping(value = "/profile/saveLifePartner", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveLifePartner(Profile lifePartner, BindingResult result, Model model){
		Optional<Profile> optioanlParentPartner = profileService.findProfile(lifePartner.getParentId());
		if(optioanlParentPartner.isPresent()){
			Profile parentPartner = optioanlParentPartner.get();
			if("MALE".equalsIgnoreCase(parentPartner.getGender())){
				lifePartner.setGender("female");
			}
			else{
				lifePartner.setGender("male");
			}
			lifePartner.setMaritalStatus(parentPartner.getMaritalStatus());
			lifePartner.setMarriageAnniversary(parentPartner.getMarriageAnniversary());
			lifePartner.setLifePartner(true);
			profileService.save(lifePartner);
			
		}
		return "redirect:/viewfulltree/1";
	}
	
	@GetMapping(value = "/getPartner/{parentProfileId}")
	public String getLifePartnerProfile(@PathVariable("parentProfileId") String parentProfileId,Model model){
		Profile lifePartner = null;
		lifePartner = new Profile();
		lifePartner.setFirstName("Test Name");
		lifePartner.setParentId(Long.parseLong(parentProfileId));
		lifePartner.setLifePartner(true);
		model.addAttribute("lifePartner", lifePartner);
		return "familytree :: life-partner-form";
	}
	
	@GetMapping(value = "/profile/{profileId}/edit", produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseDto editProfile(@PathVariable("profileId") String profileId,Model model){
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
	
	@GetMapping(value = "/profile/{familyTreeId}/initLifePartner/{parentProfileId}")
	public String setupLifePartner(@PathVariable("familyTreeId") String familyTreeId ,
			@PathVariable("parentProfileId") String parentProfileId, Model model){
		
		Profile lifePartner = null;
		lifePartner = new Profile();
		lifePartner.setParentId(Long.parseLong(parentProfileId));
		lifePartner.setLifePartner(true);
		model.addAttribute("lifePartner", lifePartner);
		model.addAttribute("familyTreeId", familyTreeId);
		return "addLifePartnerForm";
	}
	
	@GetMapping(value = "/customeProfileView/{profileId}")
	public String getCustomeProfileView(@PathVariable("profileId") String profileId, Model model){
		if(profileId!=null && profileId.compareToIgnoreCase("null")!=0){
			Optional<Profile> optProfile = profileService.findProfile(Long.valueOf(profileId));
			if(optProfile.isPresent()){
				CustomeProfile custProf = profileService.getCustomeProfile(optProfile.get());
				
				Profile lifePartner = null;
				if(custProf.getLifePartnerId() == null){
					lifePartner = new Profile();
					lifePartner.setParentId(custProf.getProfileId());
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
	
	@GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
	public String home(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,  Model model){

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() -1;
		Page<FamilyTree> treePages = (Page<FamilyTree>) familyTreeService.findAll(PageRequest.of(evalPage, evalPageSize));
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
		Optional<FamilyTree> optCurrentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		if(optCurrentFamilyTree.isPresent()){
			FamilyTree currentFamilyTree = optCurrentFamilyTree.get();
			List<Profile> profilesForEvents = profileService.findAllChildren(currentFamilyTree.getProfile().getProfileId());
			Map<Long,String> partnersProfileMap = this.pullPartnersMap(profilesForEvents);
			CustomeEventCalendar events= profileService.getCustomeEventCalender(profilesForEvents,partnersProfileMap);
			model.addAttribute("eventsCalender", events);
			model.addAttribute("familytree", currentFamilyTree);
		}
		return "/events";
	}
	
	private Map<Long,String> pullPartnersMap(List<Profile> profiles){
		return profiles.stream().filter( profile -> profile.isLifePartner()).collect(Collectors.toMap( Profile::getParentId, Profile::getProfileName));
	}
	
	@GetMapping(value = "/viewfulltree/{familyTreeId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
				Profile lifePartner = null;
				
				if(customeProfile.getLifePartnerId() == null){
					lifePartner = new Profile();
					lifePartner.setParentId(customeProfile.getProfileId());
					lifePartner.setLifePartner(true);
					model.addAttribute("lifePartner", lifePartner);
					
				}else{
					Optional<Profile> optionalLifePartner = profileService.findProfile(customeProfile.getLifePartnerId());
					if(optionalLifePartner.isPresent()){
						model.addAttribute("lifePartner", optionalLifePartner.get());
					}
					else{
						lifePartner = new Profile();
						lifePartner.setParentId(customeProfile.getProfileId());
						lifePartner.setLifePartner(true);
						model.addAttribute("lifePartner", lifePartner);
					}
				}
			} catch (JsonProcessingException e) {
				logger.warn(e.getMessage());
			}
		}
		return "/viewfulltree";
	}
	@GetMapping(value = "/aboutus", produces = MediaType.APPLICATION_JSON_VALUE)
	public String aboutUs(Model model){
		return "/aboutus";
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
