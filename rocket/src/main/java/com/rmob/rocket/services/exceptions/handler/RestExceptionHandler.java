package com.rmob.rocket.services.exceptions.handler;

import com.rmob.rocket.services.exceptions.UserNotAuthenticate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		String message = bindingResult.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining("; "));
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(UserNotAuthenticate.class)
	public ResponseEntity<Object> handleUserNotAuthenticate(UserNotAuthenticate ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		String message = ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro interno.";
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}

