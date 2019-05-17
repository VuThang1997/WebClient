package com.webClient3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Qualifier("ReportServiceImpl1")
public class ReportServiceImpl1 implements ReportService {

	private Logger logger = LoggerFactory.getLogger(ReportServiceImpl1.class);
	private RestTemplate restTemplate;

	public ReportServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ReportServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

}
