package com.webClient3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webClient3.model.Course;
import com.webClient3.service.CourseService;

@Controller
public class CourseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
	private CourseService courseService;
	
	public CourseController() {
		super();
	}

	@Autowired
	public CourseController(@Qualifier("CourseServiceImpl1") CourseService courseService) {
		super();
		this.courseService = courseService;
	}
	
	@RequestMapping(value = "/renderCreateCourse", method = RequestMethod.GET)
	public ModelAndView renderCreateCourse(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("createCourse");
		return modelAndView;
	}

	@RequestMapping(value = "/renderGetCourseInfo", method = RequestMethod.GET)
	public ModelAndView renderGetCourseInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("getCourseInfo");
		modelAndView = this.prepareForCreateCourseView(modelAndView);
		return modelAndView;
	}
	
	@RequestMapping(value = "/renderUpdateCourseInfo", method = RequestMethod.GET)
	public ModelAndView renderUpdateCourseInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("updateCourseInfo");
		modelAndView = this.prepareForCreateCourseView(modelAndView);
		return modelAndView;
	}
	
//	@RequestMapping(value = "/renderDeleteRoom", method = RequestMethod.GET)
//	public ModelAndView renderDeleteRoom(HttpSession session) {
//		if (session.getAttribute("id") == null) {
//			LOGGER.info("Redirect to login page ==============");
//			return new ModelAndView("redirect:/");
//		}
//
//		ModelAndView modelAndView = new ModelAndView("deleteRoom");
//		modelAndView = this.prepareForCreateRoomView(modelAndView);
//		return modelAndView;
//	}
	
	public ModelAndView prepareForCreateCourseView(ModelAndView modelAndView) {
		List<Course> listCourse = this.courseService.getAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course : listCourse) {
				LOGGER.info("course name = " + course.getCourseName());
			}

			modelAndView.addObject("allCourses", listCourse);
		} else {
			LOGGER.info("List course is null ===========================");
		}

		return modelAndView;
	}
}
