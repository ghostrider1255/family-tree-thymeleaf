package com.javasree.spring.familytree.web;

import static com.javasree.spring.familytree.web.utils.URLConstant.CREATE_TREE_FORM;
import static com.javasree.spring.familytree.web.utils.URLConstant.SAVE_TREE;
import static com.javasree.spring.familytree.web.utils.URLConstant.VIEW_TREE;
import static com.javasree.spring.familytree.web.utils.URLConstant.HOME;
import static com.javasree.spring.familytree.web.utils.URLConstant.EXPORT_TREE_AS_JSON;
import static com.javasree.spring.familytree.web.utils.URLConstant.DOWNLOAD_TREE_PAGE;
import static com.javasree.spring.familytree.web.utils.URLConstant.IMPORT_TREE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasree.spring.familytree.model.FamilyTree;
import com.javasree.spring.familytree.model.Profile;
import com.javasree.spring.familytree.web.dto.CustomeProfile;
import com.javasree.spring.familytree.web.dto.FamilyTreeJSON;
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

	@GetMapping(value = EXPORT_TREE_AS_JSON)
	public void downloadFamilyTree(@PathVariable("familyTreeId") String familyTreeId,HttpServletResponse response) throws IOException{
		Optional<FamilyTree> optFamilyTree = familyTreeService.findFamilyTree(Long.valueOf(familyTreeId));
		if(optFamilyTree.isPresent()){
			String familyTreeFileName = optFamilyTree.get().getFamilyTreeName() + ".json";
			familyTreeFileName = familyTreeFileName.replace(" ", "_");
			response.setContentType("application/json");
			response.setHeader("Content-Disposition", "attachment;filename=" + familyTreeFileName);
			String familyTreeAsJsonString = familyTreeService.exportTreeAsString(Long.valueOf(familyTreeId));
			BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
			outStream.write(familyTreeAsJsonString.getBytes());
			outStream.flush();
			outStream.close();
		}
	}
	
	@GetMapping(value = DOWNLOAD_TREE_PAGE)
	public String initializeImport( Model model){
		return "importTreePage";
	}
	
	@PostMapping(value = IMPORT_TREE , headers=("content-type=multipart/*"))
	public String importTree(@RequestParam("file") MultipartFile file, Model model){
		try {
			if(!file.isEmpty()){
				byte[] treeAsBytes = file.getBytes();
				String familyTreeJsonString = new String(treeAsBytes);
				FamilyTreeJSON familyTreeJson = TreeUtils.jsonStringToObject(familyTreeJsonString);
				if(familyTreeJson!=null){
					familyTreeService.save(familyTreeJson.getFamilyTree());
					familyTreeJson.getProfiles().forEach( profile -> {
						profileService.save(profile);
					});
				}
			}
		} 
		catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return "redirect:"+HOME;
	}
	
	private List<TreeNode> getTreeNodes(Long parentNodeId){
		List<TreeNode> nodes = new ArrayList<>();
		Optional<Profile> optParentProfile = profileService.findProfile(parentNodeId);
		if(optParentProfile.isPresent()){
			
			TreeNode parentNode = TreeUtils.getTreeNodeFromProfile(optParentProfile.get());
			nodes.add(parentNode);
			
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
		}
		return nodes;
	}
}
