package com.webClient3.model;

public class Course {

	private int courseID;
	private String courseName;
	private String description;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(String courseName, String description) {
		super();
		this.courseName = courseName;
		this.description = description;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
}
