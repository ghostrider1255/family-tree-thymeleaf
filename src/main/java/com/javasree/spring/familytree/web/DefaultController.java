package com.javasree.spring.familytree.web;

import static com.javasree.spring.familytree.web.utils.URLConstant.HOME;
import static com.javasree.spring.familytree.web.utils.URLConstant.ABOUT_US;
import static com.javasree.spring.familytree.web.utils.URLConstant.TREE_EVENTS;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.javasree.spring.familytree.model.FamilyTree;
import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dto.CustomeEventCalendar;
import com.javasree.spring.familytree.web.dto.PagerDto;
import com.javasree.spring.familytree.web.service.FamilyTreeService;
import com.javasree.spring.familytree.web.service.ProfileService;

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
	
	
	@GetMapping(value = ABOUT_US, produces = MediaType.APPLICATION_JSON_VALUE)
	public String aboutUs(Model model){
		logger.info("redirecting to 'aboutus' page");
		return "/aboutus";
	}
	
	@GetMapping("/")
	public String defaultHome(){
		logger.info("redirecting to 'home' page");
		return "redirect:"+ HOME ;
	}
	
	@GetMapping(value = HOME, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@GetMapping(value = TREE_EVENTS, produces = MediaType.APPLICATION_JSON_VALUE)
	public String events(@PathVariable("familyTreeId") String familyTreeId, Model model){
		Optional<FamilyTree> optCurrentFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		if(optCurrentFamilyTree.isPresent()){
			FamilyTree currentFamilyTree = optCurrentFamilyTree.get();
			List<Profile> profilesForEvents = profileService.findAllDependents(currentFamilyTree.getProfile().getProfileId());
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
}
