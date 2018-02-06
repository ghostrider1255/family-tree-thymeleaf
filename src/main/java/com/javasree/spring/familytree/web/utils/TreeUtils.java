package com.javasree.spring.familytree.web.utils;

import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.TreeNode;

public class TreeUtils {

	private TreeUtils(){
		super();
	}
	public static TreeNode getTreeNodeFromProfile(Profile profile){
		TreeNode node = new TreeNode();
		node.setId(profile.getProfileId());
		node.setTitle(profile.getFirstName());
		node.setDescription(profile.getProfileName());
		node.setParent(profile.getParentId());
		node.setGender(profile.getGender());
		return node;
	}
}
