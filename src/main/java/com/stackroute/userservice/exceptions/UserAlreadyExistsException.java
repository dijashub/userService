package com.stackroute.userservice.exceptions;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	String message;

	public UserAlreadyExistsException (String cricName){
		super (cricName);
		this.message = cricName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Cricker already [message=" + message + "]";
	}
	
}
