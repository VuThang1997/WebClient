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
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("CourseSeviceImpl1")
public class CourseSeviceImpl1 implements CourseSevice{

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseSeviceImpl1.class);
	private RestTemplate restTemplate;
	
	public CourseSeviceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public CourseSeviceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Course> getAllCourse() {
		LOGGER.info("Begin retrieving all course ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/courses/all";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<Course>> response = restTemplate.exchange(
					baseUrl, 
					HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<Course>>(){});
			LOGGER.info("Sending RestTemplate ===================");
			
			List<Course> listCourse = response.getBody();
			return listCourse;

		} catch (HttpStatusCodeException e) {
			LOGGER.info("retrieve no record ==========================");
			return null;
		}
	}
	
	
}
