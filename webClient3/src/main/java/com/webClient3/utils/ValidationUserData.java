package com.webClient3.utils;

import java.time.LocalDate;

import com.webClient3.model.User;

public interface ValidationUserData {

	String validateIdData(int id);
	
	String validateAddressData(String address);
	
	String validateBirthdayData(LocalDate birthday);
	
	String validatePhoneData(String phone);
	
	String validateFullNameData(String fullName);
	
	String validateFullUserData(User user);
	
	/**
	 * For the purpose of validate user's info in creating multiple account
	 * by file
	 * @param user - data need to be checked
	 * @return null if every fields pass; a message if a field gets error
	 */
	String validateBasicUserData(User user);
}
