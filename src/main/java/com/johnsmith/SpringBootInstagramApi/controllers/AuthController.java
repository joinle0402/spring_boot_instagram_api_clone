package com.johnsmith.SpringBootInstagramApi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnsmith.SpringBootInstagramApi.payloads.requests.LoginRequest;
import com.johnsmith.SpringBootInstagramApi.payloads.requests.RegisterRequest;
import com.johnsmith.SpringBootInstagramApi.services.interfaces.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.authService.register(request));
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.login(request));
    }
}
