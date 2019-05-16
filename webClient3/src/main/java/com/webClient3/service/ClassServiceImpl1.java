package com.webClient3.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webClient3.model.Course;
import com.webClient3.model.ReportError;
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("ClassServiceImpl1")
public class ClassServiceImpl1 implements ClassService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassServiceImpl1.class);
	private RestTemplate restTemplate;

	public ClassServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ClassServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public ReportError createMultipleStudentClass(List<String> listStudentEmail, int classID) {
		LOGGER.info("========================== Call createMultipleStudentClass service");
		ReportError report = null;
		
		//this situation should never be happened because of if-else in controller
//		if (listStudentEmail == null || listStudentEmail.isEmpty()) {
//			report = new ReportError(400, "Danh sÃ¡ch khÃ´ng cÃ³ email nÃ o!");
//			return report;
//		}
		listStudentEmail.add("" + classID);
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT 
				+ "/createMultipleStudentClass";
		
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(listStudentEmail);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsonString, header);
			ResponseEntity<ReportError> response = restTemplate.exchange(baseUrl, 
					HttpMethod.POST, requestEntity,
					ReportError.class);
			LOGGER.info("Sending RestTemplate ===================");

			report = response.getBody();
			return report;

		} catch (HttpStatusCodeException | JsonProcessingException e) {
			LOGGER.info("Error happend ==========================");
			report = new ReportError(400, "Thêm sinh viên không thành công!");
			return report;
		}
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
