package com.stackroute.userservice.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	String message;

	public UserNotFoundException(String cricName){
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
		return "User not found [message=" + message + "]";
	}
	
}
