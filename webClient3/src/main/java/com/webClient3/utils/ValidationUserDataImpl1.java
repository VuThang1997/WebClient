package com.webClient3.utils;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.webClient3.model.User;

@Component
@Qualifier("ValidationUserDataImpl1")
public class ValidationUserDataImpl1 implements ValidationUserData {

	@Override
	public String validateIdData(int id) {
		if (id < 1) {
			return "Id can not be less than 1!";
		}
		return null;
	}

	@Override
	public String validateAddressData(String address) {
		if (address == null || address.isEmpty()) {
			return "Missing Address info!";
		}
		return null;
	}

	@Override
	public String validateBirthdayData(LocalDate birthday) {
		LocalDate now = LocalDate.now();
		
		//user must not is above 80 years old or less than 18 years old
		if (birthday == null || birthday.isBefore(now.minusYears(80)) 
				|| birthday.isAfter(now.minusYears(18))) {
			return "Birthday info is missing or not in valid range! ";
		}
		return null;
	}

	@Override
	public String validatePhoneData(String phone) {
		if (phone.length() < 9) {
			return "User's phone number is too short";
		} else if (!phone.matches("[0-9]+")) {
			return "User's phone must contain only digit";
		}

		return null;
	}

	@Override
	public String validateFullNameData(String fullName) {
		if (fullName == null || fullName.isEmpty()) {
			return "Missing FullName info!";
		}
		return null;
	}

	@Override
	public String validateFullUserData(User user) {
		String errorMessage = null;

		errorMessage = validateIdData(user.getId());

		if (errorMessage == null) {
			errorMessage = validateAddressData(user.getAddress());
		}

		if (errorMessage == null) {
			errorMessage = validateFullNameData(user.getFullName());
		}

		if (errorMessage == null) {
			errorMessage = validateBirthdayData(user.getBirthDay());
		}

		if (errorMessage == null) {
			errorMessage = validatePhoneData(user.getPhone());
		}

		return errorMessage;
	}

	@Override
	public String validateBasicUserData(User user) {
		String errorMessage = null;

		errorMessage = validateFullNameData(user.getFullName());

		if (errorMessage == null) {
			errorMessage = validateBirthdayData(user.getBirthDay());
		}

		return errorMessage;
	}

}
