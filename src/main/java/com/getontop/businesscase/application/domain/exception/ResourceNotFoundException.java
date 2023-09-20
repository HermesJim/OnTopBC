package com.getontop.businesscase.application.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6705532814990906694L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
