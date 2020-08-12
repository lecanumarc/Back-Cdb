package com.excilys.java.CDB.exception;

public class UserException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserException() {}
	
	public UserException(String message) {
		super(message);
	}
}