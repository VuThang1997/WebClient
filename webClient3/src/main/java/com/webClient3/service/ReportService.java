package com.webClient3.service;

import java.util.List;

import com.webClient3.model.Course;
import com.webClient3.model.Semester;

public interface ReportService {

	List<Semester> findAllSemester();

	List<Course> findAllCourse();

}
