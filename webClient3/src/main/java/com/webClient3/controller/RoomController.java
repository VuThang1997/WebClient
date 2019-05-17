package com.webClient3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webClient3.model.Room;
import com.webClient3.service.RoomService;

@Controller
public class RoomController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);
	private RoomService roomService;
	
	public RoomController() {
		super();
	}

	@Autowired
	public RoomController(@Qualifier("RoomServiceImpl1") RoomService roomService) {
		super();
		this.roomService = roomService;
	}
	
	@RequestMapping(value = "/renderCreateRoom", method = RequestMethod.GET)
	public ModelAndView renderCreateRoom(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("createRoom");
		return modelAndView;
	}

	@RequestMapping(value = "/renderGetRoomInfo", method = RequestMethod.GET)
	public ModelAndView renderGetRoomInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("getRoomInfo");
		modelAndView = this.prepareForCreateRoomView(modelAndView);
		return modelAndView;
	}
	
	@RequestMapping(value = "/renderUpdateRoomInfo", method = RequestMethod.GET)
	public ModelAndView renderUpdateRoomInfo(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("updateRoomInfo");
		modelAndView = this.prepareForCreateRoomView(modelAndView);
		return modelAndView;
	}
	
	@RequestMapping(value = "/renderDeleteRoom", method = RequestMethod.GET)
	public ModelAndView renderDeleteRoom(HttpSession session) {
		if (session.getAttribute("id") == null) {
			LOGGER.info("Redirect to login page ==============");
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView("deleteRoom");
		modelAndView = this.prepareForCreateRoomView(modelAndView);
		return modelAndView;
	}
	
	public ModelAndView prepareForCreateRoomView(ModelAndView modelAndView) {
		List<Room> listRoom = this.roomService.getAllRoom();
		if (listRoom != null && !listRoom.isEmpty()) {
			for (Room room : listRoom) {
				LOGGER.info("room name = " + room.getRoomName());
			}

			modelAndView.addObject("allRooms", listRoom);
		} else {
			LOGGER.info("List room is null ===========================");
		}

		return modelAndView;
	}
}
