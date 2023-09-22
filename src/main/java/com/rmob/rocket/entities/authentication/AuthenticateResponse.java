package com.rmob.rocket.entities.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AuthenticateResponse {

	private Long id;
	private String token;

}
