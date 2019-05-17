package com.webClient3.model;

import java.io.Serializable;

import com.webClient3.utils.GeneralValue;

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String roomName;
	private String address;
	private double gpsLatitude;
	private double gpsLongitude;
	private String macAddress;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(String name, String address, double gpsLatitude, double gpsLongitude, String macAddress) {
		super();
		this.roomName = name;
		this.address = address;
		
		if (GeneralValue.minLatitude <= gpsLatitude && gpsLatitude <= GeneralValue.maxLatitude) {
			this.gpsLatitude = gpsLatitude;
		} else {
			this.gpsLatitude = 200;		//not a valid value
		}
		
		if (GeneralValue.minLongitude <= gpsLongitude && gpsLongitude <= GeneralValue.maxLongitude) {
			this.gpsLongitude = gpsLongitude;
		} else {
			this.gpsLongitude = 200;	//not a valid value
		}
		
		this.macAddress = macAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(double gpsLatitude) {
		if (GeneralValue.minLatitude <= gpsLatitude && gpsLatitude <= GeneralValue.maxLatitude) {
			this.gpsLatitude = gpsLatitude;
		} else {
			this.gpsLatitude = 200;		//not a valid value
		}
	}

	public double getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(double gpsLongitude) {
		if (GeneralValue.minLongitude <= gpsLongitude && gpsLongitude <= GeneralValue.maxLongitude) {
			this.gpsLongitude = gpsLongitude;
		} else {
			this.gpsLongitude = 200;	//not a valid value
		}
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
