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
import com.webClient3.model.TeacherClass;
import com.webClient3.service.RollcallService;

@Controller
public class RollcallController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RollcallController.class);
	private RollcallService teacherClassService;
	
	public RollcallController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public RollcallController(@Qualifier("RollcallServiceImpl1") RollcallService teacherClassService) {
		super();
		this.teacherClassService = teacherClassService;
	}
	
	@RequestMapping(value = "/renderTeacherRollCallView", method = RequestMethod.GET)
	public String showTeacherRollCallView(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		Integer teacherID = (Integer)session.getAttribute("id");
		List<TeacherClass> listTeacherClass = this.teacherClassService.findRecordByTeacherID(teacherID.intValue());
		for (TeacherClass teacherClass : listTeacherClass) {
			LOGGER.info("class name = " + teacherClass.getClassInstance().getClassName());
		}
		
		if (listTeacherClass != null && !listTeacherClass.isEmpty()) {
			model.put("allClasses", listTeacherClass);
			LOGGER.info("List classes is putted ===========================");
		} else {
			LOGGER.info("List classes is null ===========================");
		}

		return "teacherRollCall";
	}
	
	
}
