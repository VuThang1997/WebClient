package com.webClient3.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webClient3.model.Account;
import com.webClient3.model.Course;
import com.webClient3.model.MyFile;
import com.webClient3.model.ReportError;
import com.webClient3.service.ClassService;
import com.webClient3.service.FileService;
import com.webClient3.service.ReportService;
import com.webClient3.utils.GeneralValue;

@Controller
public class ClassController {

	private Logger logger = LoggerFactory.getLogger(ClassController.class);
	private ClassService classService;
	private FileService fileService;
	private ReportService reportService;
	
	public ClassController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ClassController(
			@Qualifier("ClassServiceImpl1") ClassService classService,
			@Qualifier("ReportServiceImpl1") ReportService reportService,
			@Qualifier("FileServiceImpl1") FileService fileService) {
		
		super();
		this.classService = classService;
		this.reportService = reportService;
		this.fileService = fileService;
	}
	
	@RequestMapping(value = "/renderCreateStudentClass", method = RequestMethod.GET)
	public String renderCreateStudentClass(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}
		
		List<Course> listCourse = this.reportService.findAllCourse();
		
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course: listCourse) {
				logger.info("course name = " + course.getCourseName());
			}
			
			model.put("allCourses", listCourse);
			logger.info("List course is putted ===========================");
		} else {
			logger.info("List course is null ===========================");
		}
		
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("report", new ReportError());
		return "createStudentClass";
	}
	
	@RequestMapping(value = "/uploadFileStudentClass", method = RequestMethod.POST)
	public String uploadFileStudentClass(@Valid @ModelAttribute("myFile") MyFile myFile,
			@Valid @ModelAttribute("report") ReportError report, ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

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
		
		List<Course> listCourse = this.reportService.findAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course: listCourse) {
				logger.info("course name = " + course.getCourseName());
			}
			
			model.put("allCourses", listCourse);
			logger.info("List course is putted ===========================");
		} else {
			logger.info("List course is null ===========================");
		}
		
		model.addAttribute("fileType", report.getErrorCode());
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("report", new ReportError());
		return "createStudentClass";
	}
	
	@RequestMapping(value = "/readFileStudentClass", method = RequestMethod.POST)
	public ModelAndView manageFile(@Valid @ModelAttribute("report") ReportError report, 
			HttpSession session) {
		if (session.getAttribute("id") == null) {
			logger.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");

		}

		ModelAndView modelAndView = new ModelAndView("createStudentClass");
		if (report.getErrorCode() == 0) {
			modelAndView.addObject("error", "Bạn phải chọn lớp học!");
			this.prepareForCreateStudentClassView(modelAndView);
			return modelAndView;
		}

		String fileName = report.getDescription();
		String linkFile = GeneralValue.FOLDER_IMPORT_FILE + File.separator + fileName;
		String studentEmail = null;
		List<String> listEmail = null;
		
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
			listEmail = new ArrayList<>();

			while (sheetIterator.hasNext()) {
				sheet = sheetIterator.next();
				logger.info("=> " + sheet.getSheetName());

				logger.info("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					row = rowIterator.next();
					
					//exclude the first row = header of table
					if (row.getRowNum() == 0) {
						continue;
					}

					// Now let's iterate over the columns of the current row
					cellIterator = row.cellIterator();

					while (cellIterator.hasNext()) {
						fieldNumber++;
						logger.info("================== fiel number = " + fieldNumber);

						cell = cellIterator.next();
						cellValue = dataFormatter.formatCellValue(cell);

						switch (fieldNumber) {
						case 1:
							studentEmail = cellValue;
							listEmail.add(studentEmail);
							logger.info("====================== Student email = " + studentEmail);
							break;
						case 2:
							fieldNumber = 0;
							break;
						}
					}
				}

			}

			int classID = report.getErrorCode();
			logger.info("class ID in controller = " + classID);
			report = this.classService.createMultipleStudentClass(listEmail, classID);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelAndView.addObject("error", "Tạo tài khoản không thành công!");
			this.prepareForCreateStudentClassView(modelAndView);
			return modelAndView;
			//model.addAttribute("error", "Tạo tài khoản không thành công!");
		}

		if (report.getErrorCode() == 200) {
			// mọi account đều hợp lệ
			if (report.getDescription().contains("0")) {
				modelAndView.addObject("error", "Tạo tài khoản thành công!");
				//model.addAttribute("error", "Tạo tài khoản thành công!");

			} else {
				String[] infoReport = report.getDescription().split("-");
				modelAndView.addObject("error", infoReport[0] + " tài khoản có dữ liệu không hợp lệ ở các dòng: " + infoReport[1]);
				//model.addAttribute("error", infoReport[0] + " tài khoản có dữ liệu không hợp lệ ở các dòng: " + infoReport[1]);
			}
		} else {
			//model.addAttribute("error", "Tạo tài khoản không thành công!");
			modelAndView.addObject("error", "Tạo tài khoản không thành công!");
		}
		
		this.prepareForCreateStudentClassView(modelAndView);
		return modelAndView;
	}
	
	public ModelAndView prepareForCreateStudentClassView(ModelAndView modelAndView) {
		modelAndView.addObject("myFile", new MyFile());
		modelAndView.addObject("report", new ReportError());
		
		List<Course> listCourse = this.reportService.findAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course: listCourse) {
				logger.info("course name = " + course.getCourseName());
			}
			
			modelAndView.addObject("allCourses", listCourse);
			logger.info("List course is putted ===========================");
		} else {
			logger.info("List course is null ===========================");
		}
		
		return modelAndView;
	}
}
