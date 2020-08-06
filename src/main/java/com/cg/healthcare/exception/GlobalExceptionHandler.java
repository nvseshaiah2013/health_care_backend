package com.cg.healthcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.healthcare.responses.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(Exception exception) {
		return new ResponseEntity<Object>(new ErrorMessage("Unknown Exception",exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
}
