package com.webClient3.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webClient3.model.Account;
import com.webClient3.model.Class;
import com.webClient3.model.Course;
import com.webClient3.model.MyFile;
import com.webClient3.model.ReportError;
import com.webClient3.model.TeacherClass;
import com.webClient3.service.ClassService;
import com.webClient3.service.CourseService;
import com.webClient3.service.FileService;
import com.webClient3.service.RollcallService;
import com.webClient3.utils.GeneralValue;

@Controller
public class RollcallController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RollcallController.class);
	private RollcallService rollcallService;
	private CourseService courseService;
	private FileService fileService;
	private ClassService classService;
	
	public RollcallController() {
		super();
	}

	@Autowired
	public RollcallController(
			@Qualifier("RollcallServiceImpl1") RollcallService rollcallService,
			@Qualifier("ClassServiceImpl1") ClassService classService,
			@Qualifier("FileServiceImpl1") FileService fileService,
			@Qualifier("CourseServiceImpl1") CourseService courseService) {
		super();
		this.rollcallService = rollcallService;
		this.classService = classService;
		this.fileService = fileService;
		this.courseService = courseService;
	}
	
	@RequestMapping(value = "/renderTeacherRollCallView", method = RequestMethod.GET)
	public String showTeacherRollCallView(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			model.addAttribute("account", new Account());
			return "login";
		}

		Integer teacherID = (Integer)session.getAttribute("id");
		List<TeacherClass> listTeacherClass = this.rollcallService.findRecordByTeacherID(teacherID.intValue());
		for (TeacherClass teacherClass : listTeacherClass) {
			LOGGER.info("class name = " + teacherClass.getClassInstance().getClassName());
		}
		
		if (listTeacherClass != null && !listTeacherClass.isEmpty()) {
			model.put("allClasses", listTeacherClass);
			LOGGER.info("List classes is putted ===========================");
		} else {
			LOGGER.info("List classes is null ===========================");
		}

		return "teacherRollCall";
	}
	
	@RequestMapping(value = "/renderRollcallStudentView", method = RequestMethod.GET)
	public ModelAndView showRollCallStudentView(ModelMap model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = this.prepareForRollcallStudentView();
	
		return modelAndView;
	}
	
	@RequestMapping(value = "/uploadFileStudentRollcall", method = RequestMethod.POST)
	public ModelAndView uploadFileStudentRollcall(@Valid @ModelAttribute("myFile") MyFile myFile,
			@Valid @ModelAttribute("classModel") Class classInstance, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		//model.addAttribute("fileType", report.getErrorCode());
		ModelAndView modelAndView = this.prepareForRollcallStudentView();

		try {
			MultipartFile multipartFile = myFile.getMultipartFile();
			String fileName = multipartFile.getOriginalFilename();
			LOGGER.info("file name = " + fileName);
			//model.addAttribute("fileName", fileName);
			modelAndView.addObject("fileName", fileName);
			
			File file = new File(this.fileService.getFolderUpload(), fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", "Không tải được file!");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/readFileStudentRollcall", method = RequestMethod.POST)
	public ModelAndView readFileRollcallStudent(
			@Valid @ModelAttribute("classModel") Class classInstance, BindingResult result,
			Model model,
			HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}
                LOGGER.info("Begin roll call for student ==========================");
		if (result.hasErrors()) {
                        LOGGER.info("binding error");
			return new ModelAndView("error");
		}
		ModelAndView modelAndView = this.prepareForRollcallStudentView();
		
		//report.ErrorCode hold ClassID value
		//report.Description hold fileName
		if (classInstance.getMaxStudent() == 0) {
                         LOGGER.info("binding error");
			modelAndView.addObject("message", "Bạn phải chọn lớp học!");
			return modelAndView;
		}
		
		if (classInstance.getCurrentLesson() == 0) {
			modelAndView.addObject("message", "Bạn phải chọn phòng học!");
			return modelAndView;
		}

		String fileName = classInstance.getClassName();
		String linkFile = GeneralValue.FOLDER_IMPORT_FILE + File.separator + fileName;
		LOGGER.info("====== File excel's name =  " + fileName);

		List<ReportError> listStudentRollcall = this.fileService.readFileExcelToGetListStudentRollcall(linkFile);
		if (listStudentRollcall == null || listStudentRollcall.isEmpty()) {
			modelAndView.addObject("message", "Điểm danh không thành công!");
		} else {
			int classID = classInstance.getMaxStudent();
			int roomID = classInstance.getCurrentLesson();
			LOGGER.info("class ID in controller = " + classID);
			ReportError report = this.rollcallService.rollcallMultipleStudent(listStudentRollcall, classID, roomID);

			if (report.getErrorCode() == 200) {
				if (report.getDescription().equalsIgnoreCase("0-")) {
					modelAndView.addObject("message", "Điểm danh thành công!");

				} else if (report.getDescription().equalsIgnoreCase("All accounts are invalid!")) {
					modelAndView.addObject("message", "Không có trường hợp nào hợp lệ!");

				} else {
					String[] partsOfReport = report.getDescription().split("-");
					modelAndView.addObject("message",
							partsOfReport[0] + " tài khoản không hợp lệ ở các dòng: " + partsOfReport[1]);
				}
			} else {
				modelAndView.addObject("message", "Tạo tài khoản không thành công!");
			}
		}

		return modelAndView;
	}
	
	private ModelAndView prepareForRollcallStudentView() {
		ModelAndView modelAndView = new ModelAndView("rollcallStudent");
		modelAndView.addObject("myFile", new MyFile());
		modelAndView.addObject("classModel", new Class());
		
		List<Course> listCourses = this.courseService.getAllCourse();
		if (listCourses != null && !listCourses.isEmpty()) {
			for (Course course : listCourses) {
				LOGGER.info("course name = " + course.getCourseName());
			}

			modelAndView.addObject("allCourses", listCourses);
		} else {
			LOGGER.info("List course is null ===========================");
		}
		
		return modelAndView;
	}
}
