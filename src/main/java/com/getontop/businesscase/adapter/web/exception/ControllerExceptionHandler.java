package com.getontop.businesscase.adapter.web.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.getontop.businesscase.application.domain.exception.ResourceNotFoundException;
import com.getontop.businesscase.application.domain.exception.ThresholdExceededException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        exception.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.NOT_FOUND.value(),
		        new Date(),
		        exception.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.INTERNAL_SERVER_ERROR.value(),
		        new Date(),
		        exception.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ThresholdExceededException.class)
	public final ResponseEntity<ErrorMessage> handleThresholdExceededException(ThresholdExceededException exception, WebRequest request){
		ErrorMessage message = new ErrorMessage(
		        HttpStatus.INTERNAL_SERVER_ERROR.value(),
		        new Date(),
		        exception.getMessage(),
		        request.getDescription(false));
		    
		    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
