package com.webClient3.controller;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webClient3.enumData.AfternoonTimeFrame;
import com.webClient3.enumData.MorningTimeFrame;
import com.webClient3.model.ClassRoom;
import com.webClient3.model.Course;
import com.webClient3.model.MyFile;
import com.webClient3.model.ReportError;
import com.webClient3.model.Room;
import com.webClient3.service.ClassService;
import com.webClient3.service.CourseService;
import com.webClient3.service.FileService;
import com.webClient3.service.RoomService;
import com.webClient3.utils.GeneralValue;

@Controller
public class ClassRoomController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomController.class);
	private RoomService roomService;
	private FileService fileService;
	private ClassService classService;
	private CourseService courseService;

	public ClassRoomController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ClassRoomController(@Qualifier("RoomServiceImpl1") RoomService roomService,
			@Qualifier("FileServiceImpl1") FileService fileService,
			@Qualifier("ClassServiceImpl1") ClassService classService,
			@Qualifier("CourseServiceImpl1") CourseService courseService) {
		super();
		this.roomService = roomService;
		this.fileService = fileService;
		this.classService = classService;
		this.courseService = courseService;
	}

	@RequestMapping(value = "/renderCreateClassRoom", method = RequestMethod.GET)
	public ModelAndView renderCreateClassRoom(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("createClassRoom");
		modelAndView = this.prepareForCreateClassRoomView(modelAndView);
		return modelAndView;
	}

	@RequestMapping(value = "/uploadClassRoomFile", method = RequestMethod.POST)
	public ModelAndView uploadClassRoomFile(@Valid @ModelAttribute("myFile") MyFile myFile,
			@Valid @ModelAttribute("roomIdHolder") ReportError roomIdHolder, HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("createClassRoom");
		modelAndView = this.prepareForCreateClassRoomView(modelAndView);

		try {
			MultipartFile multipartFile = myFile.getMultipartFile();
			String fileName = multipartFile.getOriginalFilename();
			LOGGER.info("file name = " + fileName);
			modelAndView.addObject("fileName", fileName);

			File file = new File(this.fileService.getFolderUpload(), fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", "Không tải được file!");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/readClassRoomFile", method = RequestMethod.POST)
	public ModelAndView readClassRoomFile(@Valid @ModelAttribute("roomIdHolder") ReportError roomIdHolder,
			BindingResult result, Model model, HttpSession session) {

		if (result.hasErrors()) {
			LOGGER.info("binding error");
			return new ModelAndView("error");
		}

		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}
		LOGGER.info("Begin read ClassRoom excel file ==========================");

		ModelAndView modelAndView = new ModelAndView("createClassRoom");
		modelAndView = this.prepareForCreateClassRoomView(modelAndView);

		// report.ErrorCode hold ClassID value

		String fileName = roomIdHolder.getDescription();
		String linkFile = GeneralValue.FOLDER_IMPORT_FILE + File.separator + fileName;
		LOGGER.info("====== File excel's name =  " + fileName);

		List<ClassRoom> listClassRoom = this.fileService.readFileExcelForListClassRoom(linkFile);
		if (listClassRoom == null || listClassRoom.isEmpty()) {
			modelAndView.addObject("message", "Thêm tiết học không thành công!");
		} else {
			int roomID = roomIdHolder.getErrorCode();
			LOGGER.info("room ID in controller = " + roomID);
			ReportError report = this.classService.creatMultipleClassRoom(listClassRoom, roomID);
			//ReportError report = this.classService.creatMultipleClassRoom(listClassRoom, 3);

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


	@RequestMapping(value = "/renderUpdateClassRoom", method = RequestMethod.GET)
	public ModelAndView renderUpdateClassRoom(HttpSession session) {

		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		LOGGER.info("Begin update ClassRoom ==========================");

		ModelAndView modelAndView = new ModelAndView("updateClassRoom");
		modelAndView = this.prepareForUpdateClassRoom(modelAndView);

		return modelAndView;
	}

	public ModelAndView prepareForCreateClassRoomView(ModelAndView modelAndView) {
		modelAndView.addObject("myFile", new MyFile());
		modelAndView.addObject("roomIdHolder", new ReportError());

		List<Room> listRoom = this.roomService.getAllRoom();
		if (listRoom != null && !listRoom.isEmpty()) {
			for (Room room : listRoom) {
				LOGGER.info("room name = " + room.getRoomName());
			}
			modelAndView.addObject("allRooms", listRoom);
		} else {
			LOGGER.info("List room is null ===========================");
		}

		List<Course> listCourse = this.courseService.getAllCourse();
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
	
	public ModelAndView prepareForUpdateClassRoom(ModelAndView modelAndView) {
		List<Course> listCourse = this.courseService.getAllCourse();
		if (listCourse != null && !listCourse.isEmpty()) {
			for (Course course : listCourse) {
				LOGGER.info("course name = " + course.getCourseName());
			}
			modelAndView.addObject("allCourses", listCourse);
		} else {
			LOGGER.info("List course is null ===========================");
		}
		
		List<Room> listRooms = this.roomService.getAllRoom();
		if (listRooms != null && !listRooms.isEmpty()) {
			for (Room room : listRooms) {
				LOGGER.info("room name = " + room.getRoomName());
			}
			modelAndView.addObject("allRooms", listRooms);
		}
		
		List<LocalTime> listBeginTime = new ArrayList<LocalTime>();
		List<LocalTime> listEndTime = new ArrayList<LocalTime>();
		for (MorningTimeFrame timeFrame: MorningTimeFrame.values()) {
			listBeginTime.add(timeFrame.getValue());
			listEndTime.add(timeFrame.getValue().plusMinutes(45));
		}
		
		for (AfternoonTimeFrame timeFrame: AfternoonTimeFrame.values()) {
			listBeginTime.add(timeFrame.getValue());
			listEndTime.add(timeFrame.getValue().plusMinutes(45));
		}

		modelAndView.addObject("allBeginTime", listBeginTime);
		modelAndView.addObject("allFinishTime", listEndTime);
		return modelAndView;
	}


	@RequestMapping(value = "/renderTimetableView", method = RequestMethod.GET)
	public ModelAndView renderTimetableView(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}
		
		Integer accountID = (Integer)session.getAttribute("id");
		Integer role = (Integer)session.getAttribute("role");

		LOGGER.info("Begin render Timetable ==========================");
		ModelAndView modelAndView = new ModelAndView("timetable");
		
		List<ClassRoom> listClassRooms = this.classService.getTimetable(accountID.intValue(),
				role.intValue(), 0);
		
		//classify classrooms to list for each weekday
		List<ClassRoom> listClassRoomForWeekday = null;
		for (int weekday = 2; weekday < 8; weekday++) {
			listClassRoomForWeekday = new ArrayList<>();
			for (ClassRoom tmpTarget: listClassRooms) {
				if (tmpTarget.getWeekday() == weekday) {
					listClassRoomForWeekday.add(tmpTarget);
				}
			}
			
			LOGGER.info("========================== Thu " + weekday + " has size " + listClassRoomForWeekday.size());
			String attrName = "listClassRoom" + weekday;
			LOGGER.info("========================== attr Name = " + attrName);
			modelAndView.addObject(attrName, listClassRoomForWeekday);
		}
		
		return modelAndView;
	}
	

	
}
