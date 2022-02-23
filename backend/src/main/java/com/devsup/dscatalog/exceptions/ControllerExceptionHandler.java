package com.devsup.dscatalog.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(RegisterNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(RegisterNotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError stdErr = new StandardError();

		stdErr.setTimestamp(Instant.now());
		stdErr.setStatus(status.value());
		stdErr.setError("Register not found");
		stdErr.setMessage(e.getMessage());
		stdErr.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(stdErr);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> databaseError(DataBaseException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError stdErr = new StandardError();

		stdErr.setTimestamp(Instant.now());
		stdErr.setStatus(status.value());
		stdErr.setError("Database exception");
		stdErr.setMessage(e.getMessage());
		stdErr.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(stdErr);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		ValidationError stdErr = new ValidationError();

		stdErr.setTimestamp(Instant.now());
		stdErr.setStatus(status.value());
		stdErr.setError("Validation exception");
		stdErr.setMessage(e.getMessage());
		stdErr.setPath(request.getRequestURI());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			stdErr.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(stdErr);
	}
}
