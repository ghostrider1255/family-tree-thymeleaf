package com.javasree.spring.familytree.model;

import java.util.Date;

public class Event {

	private Date eventDate;
	private String note;
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
