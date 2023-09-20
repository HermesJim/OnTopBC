package com.getontop.businesscase.adapter.web.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -278811003564263699L;
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

}
