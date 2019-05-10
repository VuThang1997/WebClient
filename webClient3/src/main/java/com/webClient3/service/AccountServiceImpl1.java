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

import com.webClient3.model.Account;
import com.webClient3.model.ReportError;

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
		String baseUrl = "http://localhost:8080/createMultipleAccount";
		ReportError report = null;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(listAccounts, header);

		// Map<String, List<Account>> params = new HashMap<String, List<Account>>();
		// params.put("listAccounts", listAccounts);

		try {
			// ReportError response = restTemplate.postForObject(baseUrl, listAccounts,
			// ReportError.class);
			ResponseEntity<ReportError> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, ReportError.class);
			logger.info("Sending RestTemplate ===================");

			report = response.getBody();
			return report;

		} catch (HttpStatusCodeException e) {
			logger.info("Error happend ==========================");
			report = new ReportError(400, "error happened");
			return report;
		}

	}

}
