package com.rmob.rocket.entities.authentication;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticateRequest {

	private String email;
	private String password;

}
