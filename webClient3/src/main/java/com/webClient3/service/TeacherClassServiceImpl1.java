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

import com.webClient3.model.Semester;
import com.webClient3.model.TeacherClass;
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("TeacherClassServiceImpl1")
public class TeacherClassServiceImpl1 implements TeacherClassService{
	
	private Logger logger = LoggerFactory.getLogger(TeacherClassServiceImpl1.class);
	private RestTemplate restTemplate;

	public TeacherClassServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public TeacherClassServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<TeacherClass> findRecordByTeacherID(int teacherID) {
		logger.info("Begin retrieving teacher-class records ==========================");
		String baseUrl = "http://" + GeneralValue.HOST + "/teacherClass?teacherID=" + teacherID;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<TeacherClass>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<TeacherClass>>(){});
			logger.info("Sending RestTemplate ===================");
			
			List<TeacherClass> listRecords = response.getBody();
			logger.info("list records = " + listRecords.size());
			return listRecords;

		} catch (HttpStatusCodeException e) {
			logger.info("retrieve no record ==========================");
			return null;
		}
	}

}
