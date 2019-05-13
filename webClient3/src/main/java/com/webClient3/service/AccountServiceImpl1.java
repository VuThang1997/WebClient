package com.webClient3.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.webClient3.enumData.AccountStatus;
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
		int listSize = listAccounts.size(); // 2 lists have the same size

		for (int i = 0; i < listSize; i++) {
			tmpAccount = listAccounts.get(i);
			if (this.validationAccountData.validateBasicAccountData(tmpAccount) != null) {
				invalidAccount++;

				// when read excel template, row 1 is excluded
				// so i = 0 is the col No.2
				rowsOfInvalidAccount += (i + 2) + ", ";
				continue;
			}

			// check user info is invalid
			tmpUser = listUser.get(i);
			if (this.validationUserData.validateBasicUserData(tmpUser) != null) {
				invalidAccount++;
				rowsOfInvalidAccount += (i + 2) + ", ";
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
			String[] infoResponse = report.getDescription().split("+");
			int reponseInvalidAccount = Integer.parseInt(infoResponse[0]);
			rowsOfInvalidAccount += infoResponse[1];

			LOGGER.info("==================== rows of invalid = " + rowsOfInvalidAccount);

			report.setDescription((invalidAccount + reponseInvalidAccount) + "+" + rowsOfInvalidAccount);
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
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/registration";

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
			// user.setBirthDay(account.getBirthday());
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

	@Override
	public Account checkAccountLogin(Account account) {
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":" + GeneralValue.SERVER_CORE_PORT + "/login";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", account.getEmail());
		params.put("password", account.getPassword());

		try {
			ObjectMapper mapper = new ObjectMapper();
			ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, params, String.class);

			Account checkResult = mapper.readValue(response.getBody(), Account.class);
			if (checkResult.getRole() != AccountRole.ADMIN.getValue()
					&& checkResult.getRole() != AccountRole.TEACHER.getValue()) {
				LOGGER.info("User's role is not an admin nor a teacher");
				return null;
			}

			if (checkResult.getIsActive() == AccountStatus.INACTIVE.getValue()) {
				LOGGER.info("This account is still inactive");
				return null;
			}

			return checkResult;

		} catch (HttpStatusCodeException | IOException e) {
			LOGGER.info("Login failed");
			return null;
		}
	}

	@Override
	public HttpSession addInitSessionValue(HttpSession session, Account checkResult) {
		LOGGER.info("=========== Begin set value for session");
		session.setAttribute("id", checkResult.getId());
		session.setAttribute("role", checkResult.getRole());
		session.setAttribute("email", checkResult.getEmail());
		session.setAttribute("password", checkResult.getPassword());
		session.setAttribute("username", checkResult.getUsername());
		session.setAttribute("host", GeneralValue.SERVER_CORE_HOST);
		session.setAttribute("port", GeneralValue.SERVER_CORE_PORT);
		LOGGER.info("=========== session store: user id = " + session.getAttribute("id"));
		return session;
	}

	@Override
	public User extractUserInfo(Account account) {
		LOGGER.info("=========== Begin extract user info from account");
		String rawUserInfo = account.getUserInfo();
		
		if (rawUserInfo != null && !rawUserInfo.isBlank()) {
			
			// UserInfo = fullName + Address + Phone + birthday
			String[] userInfoParts = account.getUserInfo().split(GeneralValue.regexForSplitUserInfo);
			User userInfo = new User();
			userInfo.setFullName(userInfoParts[0]);
			userInfo.setAddress(userInfoParts[1]);
			userInfo.setPhone(userInfoParts[2]);
			
			if (userInfoParts[3] == null || userInfoParts[3].isBlank()) {
				userInfo.setBirthDay(null);
			} else {
				userInfo.setBirthDay(LocalDate.parse(userInfoParts[3]));
			}
			
			LOGGER.info("=========== User info = " + userInfo.toString());
			return userInfo;
		}
		
		return null;
	}

	@Override
	public AccountExtension updateAccountInfo(HttpSession session, AccountExtension accountExtent) {
		LOGGER.info("=========== Begin update account info");
		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":"
				+ GeneralValue.SERVER_CORE_PORT  + "/accounts?updateUser=false";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		header.add("email", session.getAttribute("email").toString());
		header.add("password", session.getAttribute("password").toString());

		//Email and Password are mandatory fields => if not update then use old values
		String newEmail = accountExtent.getEmail();
		String newPassword = accountExtent.getPassword();
		if (newEmail == null || newEmail == "null"|| newEmail.isBlank()) {
			newEmail = session.getAttribute("email").toString();
			accountExtent.setEmail(newEmail);
		}
		
		if (newPassword == null || newPassword == "null" || newPassword.isBlank()) {
			newPassword = session.getAttribute("password").toString();
			accountExtent.setPassword(newPassword);
		}
		
		LOGGER.info("=========== New email = " + newEmail);
		LOGGER.info("=========== New Password = " + newPassword);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", newEmail);
		params.put("password", newPassword);
		params.put("username", accountExtent.getUsername());
		
		//IMEI is checked, but admin and teacher don't need IMEI
		//=> use a dummy value to pass validation
		params.put("imei", "xxxxxxx");		

		HttpEntity<?> entity = new HttpEntity<>(params, header);

		try {
			ResponseEntity<String> response = restTemplate
												.exchange(baseUrl, HttpMethod.PUT, entity, String.class);
			LOGGER.info("Sending RestTemplate ===================");
			return accountExtent;

		} catch (HttpStatusCodeException e) {
			LOGGER.info("Updating account info failed");
			accountExtent.setId(-1);   //need a mark to clear state => use Id
			return accountExtent;
		}
	}

}
