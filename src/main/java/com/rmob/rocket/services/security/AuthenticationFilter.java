package com.rmob.rocket.services.security;

import com.rmob.rocket.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getServletPath().contains("/api/v1/authentication")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (request.getServletPath().contains("swagger") || request.getServletPath().contains("docs")) {
			filterChain.doFilter(request, response);
			return;
		}

		filterChain.doFilter(request, response);
	}
}
