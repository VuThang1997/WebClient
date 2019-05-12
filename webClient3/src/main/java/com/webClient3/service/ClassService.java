package com.webClient3.service;

import java.util.List;

import com.webClient3.model.ReportError;

public interface ClassService {

	ReportError createMultipleStudentClass(List<String> listStudentEmail, int classID);

}
