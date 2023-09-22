package com.rmob.rocket.controllers.authentication;

import com.rmob.rocket.entities.authentication.AuthenticateResponse;
import com.rmob.rocket.services.authentication.AuthenticationService;
import com.rmob.rocket.entities.authentication.AuthenticateRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/rocket/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;

	@PostMapping()
	@ApiOperation("Autenticar um usuario")
	public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request){

		return ResponseEntity.ok().body(service.authenticate(request));
	}
}
