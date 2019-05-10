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
import com.webClient3.model.Semester;
import com.webClient3.service.ReportService;

@Controller
public class ReportController {

	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	private ReportService reportService;

	@Autowired
	public ReportController(@Qualifier("ReportServiceImpl1") ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@RequestMapping(value = "/renderGeneralReportView1", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		List<Semester> listSemester = this.reportService.findAllSemester();
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
}
