package com.webClient3.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassController.class);
	private ClassService classService;
	private FileService fileService;
	private ReportService reportService;

	public ClassController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ClassController(@Qualifier("ClassServiceImpl1") ClassService classService,
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
			LOGGER.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		List<Course> listCourse = this.reportService.findAllCourse();

		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course : listCourse) {
				LOGGER.info("course name = " + course.getCourseName());
			}

			model.put("allCourses", listCourse);
			model.put("allCourses2", listCourse);
		} else {
			LOGGER.info("List course is null ===========================");
		}

		model.addAttribute("myFile", new MyFile());
		model.addAttribute("report", new ReportError());
		return "createStudentClass";
	}

	@RequestMapping(value = "/uploadFileStudentClass", method = RequestMethod.POST)
	public String uploadFileStudentClass(@Valid @ModelAttribute("myFile") MyFile myFile,
			@Valid @ModelAttribute("report") ReportError report, ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		try {
			MultipartFile multipartFile = myFile.getMultipartFile();

			String fileName = multipartFile.getOriginalFilename();
			LOGGER.info("file name = " + fileName);
			model.addAttribute("fileName", fileName);
			File file = new File(this.fileService.getFolderUpload(), fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Upload failed");
		}

		List<Course> listCourse = this.reportService.findAllCourse();
		
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course : listCourse) {
				LOGGER.info("course name = " + course.getCourseName());
			}

			model.put("allCourses", listCourse);
			LOGGER.info("List course is putted ===========================");
		} else {
			LOGGER.info("List course is null ===========================");
		}

		model.addAttribute("fileType", report.getErrorCode());
		model.addAttribute("myFile", new MyFile());
		model.addAttribute("report", new ReportError());
		return "createStudentClass";
	}

	@RequestMapping(value = "/readFileStudentClass", method = RequestMethod.POST)
	public ModelAndView manageFile(@Valid @ModelAttribute("report") ReportError report, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
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
		LOGGER.info("====== File excel's name =  " + fileName);

		List<String> listEmail = this.fileService.readFileExcelToGetListEmail(linkFile);
		if (listEmail == null || listEmail.isEmpty()) {
			modelAndView.addObject("error", "Tạo tài khoản không thành công!");
		} else {
			int classID = report.getErrorCode();
			LOGGER.info("class ID in controller = " + classID);
			report = this.classService.createMultipleStudentClass(listEmail, classID);

			if (report.getErrorCode() == 200) {
				if (report.getDescription() == null) {
					modelAndView.addObject("error", "Tạo tài khoản thành công!");

				} else if (report.getDescription().equalsIgnoreCase("All accounts are invalid!")) {
					modelAndView.addObject("error", "Không có tài khoản nào hợp lệ!");

				} else {
					modelAndView.addObject("error",
							"Tài khoản có dữ liệu không hợp lệ ở các dòng: " + report.getDescription());
				}
			} else {
				modelAndView.addObject("error", "Tạo tài khoản không thành công!");
			}
		}

		this.prepareForCreateStudentClassView(modelAndView);
		return modelAndView;

//		int fieldNumber = 0;
//		LOGGER.info("========================= link file  = " + linkFile);
//
//		try {
//
//			// Creating a Workbook from an Excel file (.xls or .xlsx)
//			Workbook workbook = WorkbookFactory.create(new File(linkFile));
//
//			// Create a DataFormatter to format and get each cell's value as String
//			DataFormatter dataFormatter = new DataFormatter();
//
//			Iterator<Sheet> sheetIterator = workbook.sheetIterator();
//			Iterator<Cell> cellIterator = null;
//			Sheet sheet = null;
//			Cell cell = null;
//			Row row = null;
//			String cellValue = null;
//			listEmail = new ArrayList<>();
//
//			while (sheetIterator.hasNext()) {
//				sheet = sheetIterator.next();
//				LOGGER.info("=> " + sheet.getSheetName());
//
//				LOGGER.info("\n\nIterating over Rows and Columns using Iterator\n");
//				Iterator<Row> rowIterator = sheet.rowIterator();
//				while (rowIterator.hasNext()) {
//					row = rowIterator.next();
//					
//					//exclude the first row = header of table
//					if (row.getRowNum() == 0) {
//						continue;
//					}
//
//					// Now let's iterate over the columns of the current row
//					cellIterator = row.cellIterator();
//
//					while (cellIterator.hasNext()) {
//						fieldNumber++;
//						LOGGER.info("================== fiel number = " + fieldNumber);
//
//						cell = cellIterator.next();
//						cellValue = dataFormatter.formatCellValue(cell);
//
//						switch (fieldNumber) {
//						case 1:
//							studentEmail = cellValue;
//							listEmail.add(studentEmail);
//							LOGGER.info("====================== Student email = " + studentEmail);
//							break;
//						case 2:
//							fieldNumber = 0;
//							break;
//						}
//					}
//				}
//
//			}
//
//			int classID = report.getErrorCode();
//			LOGGER.info("class ID in controller = " + classID);
//			report = this.classService.createMultipleStudentClass(listEmail, classID);
//			
//		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
//			e.printStackTrace();
//			modelAndView.addObject("error", "Tạo tài khoản không thành công!");
//			this.prepareForCreateStudentClassView(modelAndView);
//			return modelAndView;
//		}

	}

	public ModelAndView prepareForCreateStudentClassView(ModelAndView modelAndView) {
		modelAndView.addObject("myFile", new MyFile());
		modelAndView.addObject("report", new ReportError());

		List<Course> listCourse = this.reportService.findAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course : listCourse) {
				LOGGER.info("course name = " + course.getCourseName());
			}

			modelAndView.addObject("allCourses", listCourse);
		} else {
			LOGGER.info("List course is null ===========================");
		}

		return modelAndView;
	}
}
