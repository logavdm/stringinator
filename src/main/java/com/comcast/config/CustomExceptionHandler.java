package com.comcast.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.info("Bad Request exception handler...");
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
		log.info("constrain violation exception handler...");
		Map<String, String> errors = new HashMap<>();		
		Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();	
		while (iterator.hasNext()) {
			ConstraintViolation<?> violation=iterator.next();
			String field=null;
			for (Node node : violation.getPropertyPath()) {
			    field = node.getName();
			}
			errors.put(field, violation.getMessage());
		}
		return errors;
	}

}
