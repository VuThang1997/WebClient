package com.webClient3.service;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.webClient3.model.Account;
import com.webClient3.model.AccountExtension;
import com.webClient3.model.ReportError;
import com.webClient3.model.User;

public interface AccountService {

	ReportError createMultipleAccount(List<Account> listAccounts);

	ReportError createMultipleAccount(List<Account> listAccounts, List<User> listUser);
	
	ReportError createAccountManual(AccountExtension account);

	String createUserInfoString(User user);
	
	
	/**
	 * Check login info is correct and account has authority to login
	 * @param account - info need to be checked
	 * @return account if all steps are passed; null if one step is failed
	 */
	Account checkAccountLogin(Account account);

	/**
	 * After login successful, put account's info to session
	 * @param session - a HttpSession object
	 * @param checkResult - account login successful
	 */
	HttpSession addInitSessionValue(HttpSession session, Account checkResult);

	/**
	 * Extract UserInfo string in account and convert to an User object
	 * @param account 
	 * @return an User object if account has UserInfo; null if it doesn't have
	 */
	User extractUserInfo(Account account);

	AccountExtension updateAccountInfo(HttpSession session, @Valid AccountExtension accountExtent);
	
}
