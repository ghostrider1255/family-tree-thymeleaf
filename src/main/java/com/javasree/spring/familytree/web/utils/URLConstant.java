package com.javasree.spring.familytree.web.utils;

public class URLConstant {

	private static final String FAMILY_TREE_ID = "{familyTreeId}";
	public static final String FAMILY_TREE = "/familytree";
	public static final String CREATE_TREE_FORM = FAMILY_TREE + "/createtreeform";
	public static final String SAVE_TREE = FAMILY_TREE + "/savetree";
	public static final String VIEW_TREE = FAMILY_TREE + "/"+ FAMILY_TREE_ID + "/view";
	public static final String UPDATE_TREE = FAMILY_TREE + "/"+ FAMILY_TREE_ID + "/update";
	public static final String DELETE_TREE = FAMILY_TREE + "/"+ FAMILY_TREE_ID + "/delete";
	public static final String TREE_EVENTS = FAMILY_TREE + "/"+ FAMILY_TREE_ID + "/events";
	
	private static final String PROFILE_ID = "{profileId}";
	public static final String PROFILE_URL = FAMILY_TREE + "/" + FAMILY_TREE_ID + "/profile";
	public static final String PROFILE_VIEW = PROFILE_URL + "/"+ PROFILE_ID + "/view";
	public static final String SAVE_PROFILE = PROFILE_URL + "/save";
	public static final String EDIT_PROFILE = PROFILE_URL + "/"+ PROFILE_ID + "/edit";
	public static final String SAVE_LIFE_PARTNER_PROFILE = PROFILE_URL + "/saveLifePartner";
	public static final String INIT_LIFE_PARTNER_PROFILE = PROFILE_URL + "/"+ PROFILE_ID + "/initLifePartner";
	public static final String CUSTOME_PROFILE = PROFILE_URL + "/"+ PROFILE_ID + "/customeProfile";
	public static final String DELETE_PROFILE = PROFILE_URL + "/"+ PROFILE_ID + "/delete";
	
	public static final String ABOUT_US = "/aboutus";
	public static final String HOME = "/home";
	
	
	private URLConstant(){
		super();
	}
	
}
