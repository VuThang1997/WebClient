package com.webClient3.model;

import java.io.Serializable;

public class Class implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String className;
	private int maxStudent;
	private String identifyString;
	private int numberOfLessons;
	private int currentLesson;
	private String isChecked;
	private Course course;
	private Semester semester;

	public Class() {
		super();
		this.isChecked = null;
	}

	public Class(String className, int maxStudent, int numberOfLessons) {
		super();
		this.className = className;
		this.maxStudent = maxStudent;
		this.numberOfLessons = numberOfLessons;
	}

	public Class(String name, int maxStudent) {
		super();
		this.className = name;
		this.maxStudent = maxStudent;
		this.isChecked = null;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getMaxStudent() {
		return maxStudent;
	}

	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public String getIdentifyString() {
		return identifyString;
	}

	public void setIdentifyString(String identifyString) {
		this.identifyString = identifyString;
	}

	public int getCurrentLesson() {
		return currentLesson;
	}

	public void setCurrentLesson(int currentLesson) {
		this.currentLesson = currentLesson;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public int getNumberOfLessons() {
		return numberOfLessons;
	}

	public void setNumberOfLessons(int numberOfLessons) {
		this.numberOfLessons = numberOfLessons;
	}

	
}
