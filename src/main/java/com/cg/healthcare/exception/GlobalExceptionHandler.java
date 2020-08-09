package com.cg.healthcare.exception;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.healthcare.responses.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getFieldErrors().stream().forEach(error -> {

			if (!errors.containsKey(error.getField())) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
		});

		String result = "";
		Iterator<Map.Entry<String, String>> iterator = errors.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			result = result + entry.getKey() + "->" + entry.getValue() + "\n";
		}
		logger.error(result);
		return new ResponseEntity<Object>(new ErrorMessage("Invalid Attribute Exception", result),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(Exception exception) {
		return new ResponseEntity<Object>(new ErrorMessage("Unknown Exception", exception.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
}
