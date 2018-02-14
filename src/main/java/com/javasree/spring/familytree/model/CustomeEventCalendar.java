package com.javasree.spring.familytree.model;

import java.util.Date;
import java.util.List;

public class CustomeEventCalendar {

	private Date minDate;
	private Date maxDate;
	private Date defaultDate;
	private List<Event> events;
	
	public CustomeEventCalendar(){
		super();
	}
	public Date getMinDate() {
		return minDate;
	}
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	public Date getDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
