package com.rmob.rocket.services.authentication;

import com.rmob.rocket.entities.authentication.AuthenticateResponse;
import com.rmob.rocket.entities.authentication.AuthenticateRequest;

public interface AuthenticationService {

	AuthenticateResponse authenticate(AuthenticateRequest request);
}
