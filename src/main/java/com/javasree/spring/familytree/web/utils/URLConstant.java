package com.javasree.spring.familytree.web.utils;

public class URLConstant {

	public static final String PROFILE_URL="/profile";
	public static final String PROFILE_VIEW=PROFILE_URL+"/viewprofile";
	public static final String SAVE_PROFILE=PROFILE_URL+"/save";
	public static final String DELETE_PROFILE = PROFILE_URL+"/delete/{profileId}";
	
	public static final String FAMILY_TREE="/ft";
	public static final String VIEW_FAMILY_TREE= FAMILY_TREE + "/viewtree";
	
	private URLConstant(){
		super();
	}
	
}
