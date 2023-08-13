package com.johnsmith.SpringBootInstagramApi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnsmith.SpringBootInstagramApi.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAll());
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String query) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.userService.findById(id));
	}
	
	
}
