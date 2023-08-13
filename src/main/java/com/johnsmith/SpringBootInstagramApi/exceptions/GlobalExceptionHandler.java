package com.johnsmith.SpringBootInstagramApi.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException exception, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("message", exception.getMessage());
        responseBody.put("status", exception.getStatus());
        responseBody.put("path", request.getRequestURI());
        return ResponseEntity.status(exception.getStatus()).body(responseBody);
    }
	
}
