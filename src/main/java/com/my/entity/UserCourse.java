package com.my.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * Courses for which the student is registered.
 */
public class UserCourse extends Course {
	private static final long serialVersionUID = 5459985633402576626L;
	private long studentId;
	private Calendar registrationDate;
	private String formatRegistrationDate;
	private int mark;
	
	
	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public Calendar getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Calendar registrationDate) {
		this.registrationDate = registrationDate;
		this.formatRegistrationDate = getFormatRegistrationDate("yyyy-MM-dd");
	}
	
	public void setFormatRegistrationDate(String formatRegistrationDate) {
		this.formatRegistrationDate = formatRegistrationDate;
	}
	
	public String getFormatRegistrationDate() {
		return formatRegistrationDate;
	}
	
	public String getFormatRegistrationDate(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(registrationDate.getTime());
	}
	
	public int getMark() {
		return mark;
	}
	
	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCourse other = (UserCourse) obj;
		return getId() == other.getId() && Objects.equals(getName(), other.getName());
	}	
}
