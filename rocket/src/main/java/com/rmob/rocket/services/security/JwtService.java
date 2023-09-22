package com.rmob.rocket.services.security;

import java.util.UUID;

public interface JwtService {

	String generateToken(Long id, String email);

	boolean isValidToken(String token, Long id);

	Long getUserIdFromToken(String token);

	public String getEmailFromToken(String token);
}
