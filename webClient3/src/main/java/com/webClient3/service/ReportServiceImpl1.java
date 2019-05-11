package com.webClient3.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.webClient3.model.Course;
import com.webClient3.model.Semester;
import com.webClient3.utils.GeneralValue;

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

	@Override
	public List<Semester> findAllSemester() {
		logger.info("Begin retrieving all semester ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/semesters/all";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<Semester>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<Semester>>(){});
			logger.info("Sending RestTemplate ===================");
			
			List<Semester> listSemester = response.getBody();
			return listSemester;

		} catch (HttpStatusCodeException e) {
			logger.info("retrieve no record ==========================");
			return null;
		}
	}

	@Override
	public List<Course> findAllCourse() {
		logger.info("Begin retrieving all course ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/courses/all";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<Course>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<Course>>(){});
			logger.info("Sending RestTemplate ===================");
			
			List<Course> listCourse = response.getBody();
			return listCourse;

		} catch (HttpStatusCodeException e) {
			logger.info("retrieve no record ==========================");
			return null;
		}
	}

}
