package com.my.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String duration;
	private Calendar startDate;
	private String formatStartDate;
	private Calendar endDate;
	private String formatEndDate;
	private String topic;
	private User teacher;
	private String status;
	
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
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
		this.formatStartDate = getFormatDate("yyyy-MM-dd", startDate.getTime());
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
		this.formatEndDate = getFormatDate("yyyy-MM-dd", endDate.getTime());
	}
	
	public String getFormatStartDate() {
		return formatStartDate;
	}
	
	public static String getFormatDate(String pattern, Date date) {
		if (pattern.isBlank()) {
			throw new IllegalArgumentException("Invalid date pattern!");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	public void setFormatStartDate(String formatStartDate) {
		this.formatStartDate = formatStartDate;
	}

	public String getFormatEndDate() {
		return formatEndDate;
	}

	public void setFormatEndDate(String formatEndDate) {
		this.formatEndDate = formatEndDate;
	}

	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public User getTeacher() {
		return teacher;
	}
	
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
		Course other = (Course) obj;
		return id == other.id && Objects.equals(name, other.name);
	}	
}
