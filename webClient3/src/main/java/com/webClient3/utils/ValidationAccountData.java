package com.webClient3.utils;

import com.webClient3.model.Account;

public interface ValidationAccountData {

	String validateEmailData(String email);
	
	String validatePasswordData(String password);
	
	String validateUsernameData(String username);
	
	String validateRoleData(int role);
	
	String validateIdData(int id);
	
	String validateImeiData(String imei);
	
	String validateBasicAccountData(Account account);
}
