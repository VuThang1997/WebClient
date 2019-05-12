package com.webClient3.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webClient3.model.Account;
import com.webClient3.model.AccountExtension;
import com.webClient3.model.MyFile;
import com.webClient3.model.ReportError;
import com.webClient3.service.ClassManagementService;

@Controller
public class ClassManagementController {

	private Logger logger = LoggerFactory.getLogger(ClassManagementController.class);
	private ClassManagementService classManagementService;
	
	public ClassManagementController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ClassManagementController(
			@Qualifier("ClassManagementServiceImpl1") ClassManagementService classManagementService) {
		
		super();
		this.classManagementService = classManagementService;
	}
	
	@RequestMapping(value = "/renderCreateStudentClass", method = RequestMethod.GET)
	public String renderCreateStudentClass(Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("report", new ReportError());
		return "createAccount";
	}
	
}
