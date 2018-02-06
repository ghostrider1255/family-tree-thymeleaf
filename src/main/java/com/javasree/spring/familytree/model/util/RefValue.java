package com.javasree.spring.familytree.model.util;

public class RefValue {
	
	private Long refValueId;
	private String refValueCode;
	private String refValueDesc;
	private String refValueName;
	private boolean refValueStatus;
	
	public RefValue(){
		super();
	}
	public Long getRefValueId() {
		return refValueId;
	}
	public void setRefValueId(Long refValueId) {
		this.refValueId = refValueId;
	}
	public String getRefValueCode() {
		return refValueCode;
	}
	public void setRefValueCode(String refValueCode) {
		this.refValueCode = refValueCode;
	}
	public String getRefValueDesc() {
		return refValueDesc;
	}
	public void setRefValueDesc(String refValueDesc) {
		this.refValueDesc = refValueDesc;
	}
	public boolean isRefValueStatus() {
		return refValueStatus;
	}
	public void setRefValueStatus(boolean refValueStatus) {
		this.refValueStatus = refValueStatus;
	}
	public String getRefValueName() {
		return refValueName;
	}
	public void setRefValueName(String refValueName) {
		this.refValueName = refValueName;
	}
	
	
}
