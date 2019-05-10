package com.webClient3.model;

import java.io.Serializable;
import com.webClient3.model.Class;

public class TeacherClass implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Class classInstance;
	private Account account;
	private String listRollCall;
	private int isTeaching;

	public TeacherClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherClass(String listRollCall, int isTeaching) {
		super();
		this.listRollCall = listRollCall;
		this.isTeaching = isTeaching;
	}

	public TeacherClass(Class classInstance, Account account) {
		super();
		this.classInstance = classInstance;
		this.account = account;
	}

	public TeacherClass(int isTeaching) {
		super();
		this.isTeaching = isTeaching;
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

	public int getIsTeaching() {
		return isTeaching;
	}

	public void setIsTeaching(int isTeaching) {
		this.isTeaching = isTeaching;
	}

	public String getListRollCall() {
		return listRollCall;
	}

	public void setListRollCall(String listRollCall) {
		this.listRollCall = listRollCall;
	}
}
