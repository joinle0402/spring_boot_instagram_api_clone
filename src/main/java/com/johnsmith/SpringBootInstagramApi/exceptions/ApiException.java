package com.johnsmith.SpringBootInstagramApi.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ApiException extends RuntimeException {
	private HttpStatus status;
	private String message;
	
	public ApiException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
