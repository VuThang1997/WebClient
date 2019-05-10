package com.webClient3.model;

public class AccountExtension extends Account {

	private String oldEmail;
	private String oldPassword;
	private String oldUsername;
	
	public AccountExtension() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountExtension(String oldEmail, String oldPassword) {
		super();
		this.oldEmail = oldEmail;
		this.oldPassword = oldPassword;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldUsername() {
		return oldUsername;
	}

	public void setOldUsername(String oldUsername) {
		this.oldUsername = oldUsername;
	}
	
	
	
}
