package com.webClient3.model;

import java.io.Serializable;
import java.time.LocalTime;

public class ClassRoom implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private LocalTime beginAt;
	private LocalTime finishAt;
	private int weekday;
	private Class classInstance;
	private Room room;

	public ClassRoom() {
		super();
	}

	public ClassRoom(int id, LocalTime beginAt, LocalTime finishAt, int weekday) {
		super();
		this.id = id;
		this.beginAt = beginAt;
		this.finishAt = finishAt;
		this.weekday = weekday;
	}

	public ClassRoom(LocalTime beginAt, LocalTime finishAt, int weekday) {
		super();
		this.beginAt = beginAt;
		this.finishAt = finishAt;
		this.weekday = weekday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public LocalTime getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(LocalTime beginAt) {
		this.beginAt = beginAt;
	}

	public LocalTime getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(LocalTime finishAt) {
		this.finishAt = finishAt;
	}

	public Class getClassInstance() {
		return classInstance;
	}

	public void setClassInstance(Class classInstance) {
		this.classInstance = classInstance;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
}
