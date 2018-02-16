package com.javasree.spring.familytree.model;

import java.util.List;

public class CustomeEventCalendar {

	private String minDate;
	private String maxDate;
	private String defaultDate;
	private List<Event> events;
	
	public CustomeEventCalendar(){
		super();
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public String getDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}
}
