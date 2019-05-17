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

import com.webClient3.model.Semester;
import com.webClient3.service.SemesterService;

@Controller
public class SemesterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SemesterController.class);
	private SemesterService semesterService;
	
	public SemesterController() {
		super();
	}

	@Autowired
	public SemesterController(@Qualifier("SemesterServiceImpl1") SemesterService semesterService) {
		super();
		this.semesterService = semesterService;
	}
	
	@RequestMapping(value = "/renderGetSemesterInfo", method = RequestMethod.GET)
	public ModelAndView renderGetSemesterInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("getSemesterInfo");
		modelAndView = this.prepareForCreateSemesterView(modelAndView);
		return modelAndView;
	}

	@RequestMapping(value = "/renderUpdateSemesterInfo", method = RequestMethod.GET)
	public ModelAndView renderUpdateSemesterInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("updateSemesterInfo");
		modelAndView = this.prepareForCreateSemesterView(modelAndView);
		return modelAndView;
	}
	
	public ModelAndView prepareForCreateSemesterView(ModelAndView modelAndView) {
		List<Semester> listSemester = this.semesterService.findAllSemester();
		if (listSemester != null && !listSemester.isEmpty()) {
			for (Semester semester : listSemester) {
				LOGGER.info("semester name = " + semester.getSemesterName());
			}

			modelAndView.addObject("allSemesters", listSemester);
		} else {
			LOGGER.info("List Semester is null ===========================");
		}

		return modelAndView;
	}
}
