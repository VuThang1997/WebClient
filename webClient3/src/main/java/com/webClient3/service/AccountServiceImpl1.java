package com.webClient3.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.webClient3.enumData.AccountRole;
import com.webClient3.model.Account;
import com.webClient3.model.AccountExtension;
import com.webClient3.model.ReportError;
import com.webClient3.model.User;
import com.webClient3.utils.GeneralValue;
import com.webClient3.utils.ValidationAccountData;
import com.webClient3.utils.ValidationUserData;

@Service
@Qualifier("AccountServiceImpl1")
public class AccountServiceImpl1 implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl1.class);
	private RestTemplate restTemplate;
	private ValidationUserData validationUserData;
	private ValidationAccountData validationAccountData;

	public AccountServiceImpl1() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public AccountServiceImpl1(RestTemplate restTemplate,
			@Qualifier("ValidationUserDataImpl1") ValidationUserData validationUserData,
			@Qualifier("ValidationAccountDataImpl1") ValidationAccountData validationAccountData) {
		super();
		this.restTemplate = restTemplate;
		this.validationUserData = validationUserData;
		this.validationAccountData = validationAccountData;
	}

	@Override
	public ReportError createMultipleAccount(List<Account> listAccounts) {
		LOGGER.info("Begin creating accounts ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/createMultipleAccount";
		ReportError report = null;

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(listAccounts);

			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsonString, header);

			ResponseEntity<ReportError> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity,
					ReportError.class);
			LOGGER.info("Sending RestTemplate ===================");

			report = response.getBody();
			return report;

		} catch (HttpStatusCodeException | JsonProcessingException e) {
			LOGGER.info("Error happend ==========================");
			report = new ReportError(400, "error happened");
			return report;
		}

	}

	@Override
	public ReportError createMultipleAccount(List<Account> listAccounts, List<User> listUser) {
		LOGGER.info("Begin creating accounts ==========================");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/createMultipleAccount";
		ReportError report = null;
		Account tmpAccount = null;
		User tmpUser = null;
		String userInfo = null;
		String rowsOfInvalidAccount = "";
		int invalidAccount = 0;
		int listSize = listAccounts.size();			//2 lists have the same size

		for (int i = 0; i < listSize; i++) {
			tmpAccount = listAccounts.get(i);
			if (this.validationAccountData.validateBasicAccountData(tmpAccount) != null) {
				invalidAccount++;
				
				//when read excel template, row 1 is excluded
				//so i = 0 is the col No.2
				rowsOfInvalidAccount += (i+2) + ", ";
				continue;
			}

			// check user info is invalid
			tmpUser = listUser.get(i);
			if (this.validationUserData.validateBasicUserData(tmpUser) != null) {
				invalidAccount++;
				rowsOfInvalidAccount += (i+2) + ", ";
				continue;
			}

			userInfo = createUserInfoString(tmpUser);
			tmpAccount.setUserInfo(userInfo);
		}
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(listAccounts);

			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsonString, header);

			ResponseEntity<ReportError> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity,
					ReportError.class);
			LOGGER.info("Sending RestTemplate ===================");

			report = response.getBody();
			String[] infoResponse = report.getDescription().split("-");
			int reponseInvalidAccount = Integer.parseInt(infoResponse[0]);
			rowsOfInvalidAccount += infoResponse[1];
			
			LOGGER.info("==================== rows of invalid = " + rowsOfInvalidAccount);
			
			report.setDescription((invalidAccount + reponseInvalidAccount) + "-" + rowsOfInvalidAccount);
			return report;

		} catch (HttpStatusCodeException | JsonProcessingException e) {
			LOGGER.info("Error happend ==========================");
			report = new ReportError(400, rowsOfInvalidAccount);
			return report;
		}
	}

	@Override
	public String createUserInfoString(User user) {
		String userInfo = null;

		// userInfo has format: "fullName+address+phone+birthDay"
		userInfo = user.getFullName() + GeneralValue.regexForSplitUserInfo;
		userInfo += user.getAddress() + GeneralValue.regexForSplitUserInfo;
		userInfo += user.getPhone() + GeneralValue.regexForSplitUserInfo;
		userInfo += user.getBirthDay();

		return userInfo;
	}

	@Override
	public ReportError createAccountManual(AccountExtension account) {
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":"
				+ GeneralValue.SERVER_CORE_PORT + "/registration";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		boolean isValidRole = false;
		for (AccountRole value : AccountRole.values()) {
			if (value.getValue() == account.getRole()) {
				isValidRole = true;
				break;
			}
		}
		
		LOGGER.info("======================= birthday = " + account.getBirthday());

		if (isValidRole == false) {
			return new ReportError(400, "Loại tài khoản không hợp lệ!");

		} else {
			User user = new User();
			user.setAddress(account.getAddress());
			LOGGER.info("======================= address = " + user.getAddress());
			//user.setBirthDay(account.getBirthday());
			user.setBirthDay(LocalDate.parse(account.getBirthday()));
			LOGGER.info("======================= birthday = " + user.getBirthDay().toString());
			user.setFullName(account.getFullName());
			LOGGER.info("======================= full name = " + user.getFullName());
			user.setPhone(account.getPhone());
			LOGGER.info("======================= phone = " + user.getPhone());
			
			String errorMessage = this.validationUserData.validateBasicUserData(user);
			if (errorMessage != null) {
				return new ReportError(400, errorMessage);
			}
			
			String userInfo = createUserInfoString(user);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", account.getEmail());
			params.put("password", account.getPassword());
			params.put("username", account.getUsername());
			params.put("role", account.getRole());
			params.put("userInfo", userInfo);
			
			LOGGER.info("==================== account.role = " + account.getRole());

			HttpEntity<?> entity = new HttpEntity<>(params, header);

			try {
				ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
				LOGGER.info("Sending RestTemplate ===================");
				return new ReportError(200, "Tạo tài khoản thành công");

			} catch (HttpStatusCodeException e) {
				LOGGER.info("Creating account info failed");
				return new ReportError(400, "Tạo tài khoản thất bại");
			}
		}
	}

}
