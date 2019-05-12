package com.webClient3.service;

import java.util.List;

import com.webClient3.model.Account;
import com.webClient3.model.AccountExtension;
import com.webClient3.model.ReportError;
import com.webClient3.model.User;

public interface AccountService {

	ReportError createMultipleAccount(List<Account> listAccounts);

	ReportError createMultipleAccount(List<Account> listAccounts, List<User> listUser);
	
	ReportError createAccountManual(AccountExtension account);

	String createUserInfoString(User user);
}
