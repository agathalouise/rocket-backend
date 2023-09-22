package com.rmob.rocket.services.authentication;


import com.rmob.rocket.entities.authentication.AuthenticateRequest;
import com.rmob.rocket.entities.authentication.AuthenticateResponse;
import com.rmob.rocket.entities.funcionario.Funcionario;
import com.rmob.rocket.entities.user.User;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import com.rmob.rocket.services.funcionario.FuncionarioService;
import com.rmob.rocket.services.security.JwtService;
import com.rmob.rocket.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	UserService userService;

	@Autowired
	FuncionarioService funcionarioService;

	@Autowired
	JwtService jwtService;

	private static final String EMAIL_REGEX = ".*@(rocket\\.com|rocket\\.com\\.br)$";

	@Override
	public AuthenticateResponse authenticate(AuthenticateRequest request) {

		if (Pattern.matches(EMAIL_REGEX, request.getEmail())) {
			Funcionario funcionario = funcionarioService.findFuncionarioByEmail(request.getEmail());

			if (!BCrypt.checkpw(request.getPassword(), funcionario.getSenha())) {
				throw new DataIntegratyViolationException("Senha atual incorreta");
			}

			String token = jwtService.generateToken(funcionario.getId(), request.getEmail());

			log.info("token jwt {} ", token);
			AuthenticateResponse response = new AuthenticateResponse(funcionario.getId(), token);

			log.info("reponse  {} ", response);
			return response;

		} else {
			User user = userService.getUser(request.getEmail());

			if (!BCrypt.checkpw(request.getPassword(), user.getSenha())) {
				throw new DataIntegratyViolationException("Senha atual incorreta");
			}

			String token = jwtService.generateToken(user.getId(), user.getEmail());

			log.info("token jwt {} ", token);
			AuthenticateResponse response = new AuthenticateResponse(user.getId(), token);

			log.info("reponse  {} ", response);
			return response;
		}

	}

}
