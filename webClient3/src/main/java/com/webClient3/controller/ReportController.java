package com.webClient3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webClient3.model.Account;
import com.webClient3.model.Course;
import com.webClient3.model.Semester;
import com.webClient3.service.ClassService;
import com.webClient3.service.CourseService;
import com.webClient3.service.SemesterService;

@Controller
public class ReportController {

	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	private SemesterService semesterService;
	private ClassService classService;
	private CourseService courseService;

	@Autowired
	public ReportController(
			@Qualifier("SemesterServiceImpl1") SemesterService semesterService,
			@Qualifier("ClassServiceImpl1") ClassService classService,
			@Qualifier("CourseServiceImpl1") CourseService courseService) {
		super();
		this.semesterService = semesterService;
		this.classService = classService;
		this.courseService = courseService;
	}

	@RequestMapping(value = "/renderGeneralReportView1", method = RequestMethod.GET)
	public String renderGeneralReportView1(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		List<Semester> listSemester = this.semesterService.findAllSemester();
		for (Semester semester: listSemester) {
			logger.info("semester name = " + semester.getSemesterName());
		}
		if (listSemester != null && !listSemester.isEmpty()) {
			model.put("allSemester", listSemester);
			logger.info("List semester is putted ===========================");
		} else {
			logger.info("List semester is null ===========================");
		}
		
		return "generalReportForStudent";
	}
	
	@RequestMapping(value = "/renderGeneralReportForClass", method = RequestMethod.GET)
	public String renderGeneralReportForClass(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		List<Course> listCourse = this.courseService.getAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course: listCourse) {
				logger.info("course name = " + course.getCourseName());
			}
			
			model.put("allCourses", listCourse);
			logger.info("List course is putted ===========================");
		} else {
			logger.info("List course is null ===========================");
		}
		
		return "generalReportForClass";
	}
}
