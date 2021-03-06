package com.webClient3.service;

import java.util.List;

import com.webClient3.model.ClassRoom;
import com.webClient3.model.ReportError;

public interface ClassService {

	ReportError createMultipleStudentClass(List<String> listStudentEmail, int classID);

	ReportError creatMultipleClassRoom(List<ClassRoom> listClassRoom, int roomID);
	
	List<ClassRoom> getTimetable(int accountID, int accountRole, int semesterID);

}
