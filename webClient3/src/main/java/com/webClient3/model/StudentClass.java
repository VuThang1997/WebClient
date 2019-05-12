package com.webClient3.model;

import java.io.Serializable;

public class StudentClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Class classInstance;
	private Account account;
	private int isLearning;
	private String isChecked;
	private String listRollCall;

	public StudentClass() {
		super();
		this.listRollCall = "";
	}

	public StudentClass(int isLearning) {
		super();
		this.isLearning = isLearning;
	}

	public StudentClass(int isLearning, String listRollCall) {
		super();
		this.isLearning = isLearning;
		this.listRollCall = listRollCall;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Class getClassInstance() {
		return classInstance;
	}

	public void setClassInstance(Class classInstance) {
		this.classInstance = classInstance;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getIsLearning() {
		return isLearning;
	}

	public void setIsLearning(int isLearning) {
		this.isLearning = isLearning;
	}

	public String getListRollCall() {
		return listRollCall;
	}

	public void setListRollCall(String listRollCall) {
		this.listRollCall = listRollCall;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
}
