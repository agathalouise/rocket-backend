package com.rmob.rocket.services.exceptions;

public class UserNotAuthenticate extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotAuthenticate(String message) {
		super(message);
	}
	
}
