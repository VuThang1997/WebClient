package com.webClient3.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.webClient3.model.Account;
import com.webClient3.model.ReportError;
import com.webClient3.utils.GeneralValue;

@Service
@Qualifier("ReportServiceImpl1")
public class AccountServiceImpl1 implements AccountService {

	private Logger logger = LoggerFactory.getLogger(ReportServiceImpl1.class);
	private RestTemplate restTemplate;

	public AccountServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public AccountServiceImpl1(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public ReportError createMultipleAccount(List<Account> listAccounts) {
		logger.info("Begin creating accounts ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/createMultipleAccount";
		ReportError report = null;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(listAccounts);
		
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsonString, header);

		// Map<String, List<Account>> params = new HashMap<String, List<Account>>();
		// params.put("listAccounts", listAccounts);
			// ReportError response = restTemplate.postForObject(baseUrl, listAccounts,
			// ReportError.class);
			ResponseEntity<ReportError> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, ReportError.class);
			logger.info("Sending RestTemplate ===================");

			report = response.getBody();
			return report;

		} catch (HttpStatusCodeException | JsonProcessingException e) {
			logger.info("Error happend ==========================");
			report = new ReportError(400, "error happened");
			return report;
		}

	}

}
