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
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("SemesterServiceImpl1")
public class SemesterServiceImpl1 implements SemesterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SemesterServiceImpl1.class);
	private RestTemplate restTemplate;
	
	@Autowired
	public SemesterServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public SemesterServiceImpl1() {
		super();
	}

	@Override
	public List<Semester> findAllSemester() {
		LOGGER.info("Begin retrieving all semester ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT 
				+ "/semesters/all";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		try {
			ResponseEntity<List<Semester>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<Semester>>(){});
			LOGGER.info("Sending RestTemplate ===================");
			
			List<Semester> listSemester = response.getBody();
			return listSemester;

		} catch (HttpStatusCodeException e) {
			LOGGER.info("retrieve no record ==========================");
			return null;
		}
	}
	
	
}
