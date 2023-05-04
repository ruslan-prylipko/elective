package com.my.entity;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

public class ShortCourse {
	private long id;
	private String name;
	private String duration;
	private Date startDate;
	private Date endDate;
	private long topicId;
	private long teacherId;
	private long statusId;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = new Date(startDate.getTimeInMillis());
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = new Date(endDate.getTimeInMillis());
	}
	
	public long getTopicId() {
		return topicId;
	}
	
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	
	public long getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}
	
	public long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortCourse other = (ShortCourse) obj;
		return id == other.id && Objects.equals(name, other.name);
	}
}
