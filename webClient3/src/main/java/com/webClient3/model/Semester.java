package com.webClient3.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Semester implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int semesterID;
	private String semesterName;
	private LocalDate beginDate;
	private LocalDate endDate;

	public Semester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Semester(String semesterName, LocalDate beginDate, LocalDate endDate) {
		super();
		this.semesterName = semesterName;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public Semester(int semesterID, String semesterName, LocalDate beginDate, LocalDate endDate) {
		super();
		this.semesterID = semesterID;
		this.semesterName = semesterName;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getSemesterID() {
		return semesterID;
	}

	public void setSemesterID(int semesterID) {
		this.semesterID = semesterID;
	}

}
