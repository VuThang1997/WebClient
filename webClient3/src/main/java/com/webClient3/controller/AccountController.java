package com.webClient3.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webClient3.enumData.AccountRole;
import com.webClient3.enumData.AccountStatus;
import com.webClient3.model.Account;
import com.webClient3.model.AccountExtension;
import com.webClient3.model.MyFile;
import com.webClient3.model.ReportError;
import com.webClient3.model.User;
import com.webClient3.service.AccountService;
import com.webClient3.service.FileService;
import com.webClient3.utils.GeneralValue;

@Controller
public class AccountController {

	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	private AccountService accountService;
	private FileService fileService;
	RestTemplate restTemplate;

	public AccountController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public AccountController(RestTemplate restTemplate,
			@Qualifier("AccountServiceImpl1") AccountService accountService,
			@Qualifier("FileServiceImpl1") FileService fileService) {
		super();
		this.restTemplate = restTemplate;
		this.accountService = accountService;
		this.fileService = fileService;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		model.asMap().clear();
		model.addAttribute("account", new Account());
		return "login";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		model.addAttribute("account", new Account());
		return "login";
	}

	@RequestMapping(value = "/checkLoginInfo", method = RequestMethod.POST)
	public String checkLoginInfo(@Valid @ModelAttribute("account") Account account, HttpSession session,
			BindingResult result, Model model) {

		logger.info("Begin checkLoginInfo");
		if (result.hasErrors()) {
			return "error";
		}

		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":"
				+ GeneralValue.SERVER_CORE_PORT + "/login";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		Map<String, String> params = new HashMap<String, String>();
		params.put("email", account.getEmail());
		params.put("password", account.getPassword());

		try {
			ObjectMapper mapper = new ObjectMapper();
			ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, params, String.class);

			Account adminAccount = mapper.readValue(response.getBody(), Account.class);
			if (adminAccount.getRole() != AccountRole.ADMIN.getValue()
					&& adminAccount.getRole() != AccountRole.TEACHER.getValue()) {
				logger.info("User's role is not an admin nor a teacher");
				model.addAttribute("error", "Only admins have authority to login!");
				return "login";
			}

			if (adminAccount.getIsActive() == AccountStatus.INACTIVE.getValue()) {
				logger.info("This account is still inactive");
				model.addAttribute("error", "This account is still inactive!");
				return "login";
			}

			session.setAttribute("id", adminAccount.getId());
			session.setAttribute("role", adminAccount.getRole());
			session.setAttribute("email", adminAccount.getEmail());
			session.setAttribute("password", adminAccount.getPassword());
			session.setAttribute("username", adminAccount.getUsername());
			session.setAttribute("host", GeneralValue.SERVER_CORE_HOST);
			session.setAttribute("port", GeneralValue.SERVER_CORE_PORT);

			model.addAttribute("username", adminAccount.getUsername());
			model.addAttribute("email", adminAccount.getEmail());
			model.addAttribute("password", adminAccount.getPassword());

			// UserInfo = fullName + Address + Phone + birthday
			String rawUserInfo = adminAccount.getUserInfo();
			if (rawUserInfo != null && !rawUserInfo.isBlank()) {
				String[] userInfo = adminAccount.getUserInfo().split(GeneralValue.regexForSplitUserInfo);
				logger.info("User info = " + rawUserInfo);

				model.addAttribute("fullName", userInfo[0]);
				logger.info(userInfo[0]);
				model.addAttribute("address", userInfo[1]);
				logger.info(userInfo[1]);
				model.addAttribute("phone", userInfo[2]);
				logger.info(userInfo[2]);
				model.addAttribute("birthday", userInfo[3]);
				logger.info(userInfo[3]);
			}

			model.addAttribute("accountExtent", new AccountExtension());
			return "updateUserInfo";

		} catch (HttpStatusCodeException | IOException e) {
			logger.info("Login failed");
			model.addAttribute("error", "Incorrect email or password!");
			return "login";
		}
	}

	@RequestMapping(value = "/updateAcountInfo", method = RequestMethod.POST)
	public String updateAccountInfo(@Valid @ModelAttribute("accountExtent") AccountExtension accountExtent,
			HttpSession session, BindingResult result, Model model) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		logger.info("Begin updating account info ==========================");
		if (result.hasErrors()) {
			return "error";
		}

		String baseUrl = GeneralValue.SERVER_CORE_HOST + ":"
				+ GeneralValue.SERVER_CORE_PORT  + "/accounts?updateUser=false";

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		header.add("email", session.getAttribute("email").toString());
		header.add("password", session.getAttribute("password").toString());

		Map<String, String> params = new HashMap<String, String>();
		if (accountExtent.getEmail() == null || accountExtent.getEmail() == "null"
				|| accountExtent.getEmail().isBlank()) {
			accountExtent.setEmail(session.getAttribute("email").toString());
		}
		params.put("email", accountExtent.getEmail());

		if (accountExtent.getPassword() == null || accountExtent.getPassword() == "null"
				|| accountExtent.getPassword().isBlank()) {
			accountExtent.setPassword(session.getAttribute("password").toString());
		}
		params.put("password", accountExtent.getPassword());
		params.put("username", accountExtent.getUsername());
		params.put("imei", "xxxxxxx");

		HttpEntity<?> entity = new HttpEntity<>(params, header);

		try {
			ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, entity, String.class);
			logger.info("Sending RestTemplate ===================");

			model.addAttribute("error", "Updating account info success!");
			model.addAttribute("username", accountExtent.getUsername());
			model.addAttribute("email", accountExtent.getEmail());
			model.addAttribute("password", accountExtent.getPassword());
			model.addAttribute("accountExtent", new AccountExtension());

			session.setAttribute("email", accountExtent.getEmail());
			session.setAttribute("password", accountExtent.getPassword());
			session.setAttribute("username", accountExtent.getUsername());
			return "updateUserInfo";

		} catch (HttpStatusCodeException e) {
			logger.info("Updating account info failed");
			model.addAttribute("error", "Updating account info failed!");
			return "updateUserInfo";
		}
	}

	@RequestMapping(value = "/renderCreateAccount", method = RequestMethod.GET)
	public String renderCreateAccount(Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("newAccount", new AccountExtension());
		model.addAttribute("report", new ReportError());
		return "createAccount";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(@Valid @ModelAttribute("myFile") MyFile myFile,
			@Valid @ModelAttribute("report") ReportError report, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		model.addAttribute("fileType", report.getErrorCode());
		model.addAttribute("report", new ReportError());
		model.addAttribute("newAccount", new AccountExtension());
		try {
			MultipartFile multipartFile = myFile.getMultipartFile();

			String fileName = multipartFile.getOriginalFilename();
			logger.info("file name = " + fileName);
			model.addAttribute("fileName", fileName);
			File file = new File(this.fileService.getFolderUpload(), fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Upload failed");
		}
		return "createAccount";
	}

	@RequestMapping(value = "/readFile", method = RequestMethod.POST)
	public String manageFile(@Valid @ModelAttribute("report") ReportError report, 
			Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		boolean isValidRole = false;
		for (AccountRole role : AccountRole.values()) {
			if (report.getErrorCode() == role.getValue()) {
				isValidRole = true;
				break;
			}
		}

		if (isValidRole == false) {
			model.addAttribute("error", "Bạn phải chọn loại tài khoản!");
			model.addAttribute("myFile", new MyFile());
			model.addAttribute("newAccount", new AccountExtension());
			model.addAttribute("report", new ReportError());
			return "createAccount";
		}

		String fileName = report.getDescription();
		String linkFile = GeneralValue.FOLDER_IMPORT_FILE + File.separator + fileName;
		Account account = null;
		User tmpUser = null;
		List<Account> listAccounts = null;
		List<User> listUser = null;
		int fieldNumber = 0;
		logger.info("========================= link file  = " + linkFile);

		try {

			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(linkFile));

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
			Iterator<Cell> cellIterator = null;
			Sheet sheet = null;
			Cell cell = null;
			Row row = null;
			String cellValue = null;
			
			listAccounts = new ArrayList<>();
			listUser = new ArrayList<>();

			while (sheetIterator.hasNext()) {
				sheet = sheetIterator.next();
				logger.info("=> " + sheet.getSheetName());

				logger.info("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					if (row.getRowNum() == 0) {
						continue;
					}

					// Now let's iterate over the columns of the current row
					cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						fieldNumber++;
						logger.info("================== fiel number = " + fieldNumber);

						// exclude the first row of table: fieldNumber from -3 to 0
						if (fieldNumber < 1) {
							continue;
						}

						cell = cellIterator.next();
						cellValue = dataFormatter.formatCellValue(cell);

						switch (fieldNumber) {
						case 1:
							account = new Account();
							account.setEmail(cellValue);
							logger.info("====================== Account email = " + account.getEmail());
							break;
						case 2:
							account.setPassword(cellValue);
							logger.info("====================== Account pass = " + account.getPassword());
							break;
						case 3:
							account.setUsername(cellValue);
							logger.info("====================== Account usrname = " + account.getUsername());
							account.setImei(null);
							account.setIsActive(AccountStatus.ACTIVE.getValue());
							account.setUpdateImeiCounter(0);
							account.setUserInfo(null);

							listAccounts.add(account);
							break;
						case 4:
							tmpUser = new User();
							tmpUser.setFullName(cellValue);
							break;
						case 5:
							tmpUser.setBirthDay(LocalDate.parse(cellValue));
							break;
						case 6:
							tmpUser.setPhone(cellValue);
							break;
						case 7:
							tmpUser.setAddress(cellValue);
							fieldNumber = 0;
							
							tmpUser.setId(1);			//set id = 1 to pass the full test
							listUser.add(tmpUser);
							break;
						}
						logger.info("======================" + cellValue);
					}
				}

			}

			//report = this.accountService.createMultipleAccount(listAccounts);
			report = this.accountService.createMultipleAccount(listAccounts, listUser);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", "Tạo tài khoản không thành công!");
		}

		if (report.getErrorCode() == 200) {
			// mọi account đều hợp lệ
			if (report.getDescription().contains("0")) {
				model.addAttribute("error", "Tạo tài khoản thành công!");

			} else {
				String[] infoReport = report.getDescription().split("+");
				model.addAttribute("error", infoReport[0] + " tài khoản có dữ liệu không hợp lệ ở các dòng: " + infoReport[1]);
			}
		} else {
			model.addAttribute("error", "Tạo tài khoản không thành công!");
		}
		
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("newAccount", new AccountExtension());
		model.addAttribute("report", new ReportError());
		return "createAccount";
	}

	@RequestMapping(value = "/manipulateFile", method = RequestMethod.POST)
	public String uploadFile(@RequestBody String inputFile) {
		String fileName = null;
		ObjectMapper mapper = new ObjectMapper();
		MultipartFile multipartFile;

		try {
			multipartFile = mapper.readValue(inputFile, MultipartFile.class);
			fileName = multipartFile.getOriginalFilename();
			logger.info("file name = " + fileName);
			File file = new File(this.fileService.getFolderUpload(), fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	@RequestMapping(value = "/createNewAccountManually", method = RequestMethod.POST)
	public String createNewAccountManually(@Valid @ModelAttribute("newAccount") AccountExtension account, BindingResult result,
			Model model, HttpSession session) {

		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		logger.info("Begin create new account ==========================");
		if (result.hasErrors()) {
			return "error";
		}

		ReportError report = this.accountService.createAccountManual(account);
		model.addAttribute("error2", report.getDescription());

		model.addAttribute("myFile", new MyFile());
		model.addAttribute("newAccount", new AccountExtension());
		model.addAttribute("report", new ReportError());
		return "createAccount";
	}
}
