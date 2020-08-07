package com.excilys.java.CDB.exception;

public class ComputerNameException extends ComputerException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public ComputerNameException() {
		message = "The name is mandatory.";
	}

	public String getMessage() {
		return message;
	}
	
}
