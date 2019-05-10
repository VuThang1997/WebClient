package com.webClient3.service;

import java.util.List;

import com.webClient3.model.Account;
import com.webClient3.model.ReportError;

public interface AccountService {

	ReportError createMultipleAccount(List<Account> listAccounts);

}
