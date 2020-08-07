package com.excilys.java.CDB.exception;

public class ComputerDateException extends ComputerException {

	private static final long serialVersionUID = 1L;
	private String message;

	public ComputerDateException() {
		message = "Introduced date must be before discontinued date.";
	}

	public String getMessage() {
		return message;
	}
}
