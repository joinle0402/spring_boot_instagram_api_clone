package com.johnsmith.SpringBootInstagramApi.services.interfaces;

import com.johnsmith.SpringBootInstagramApi.models.User;
import com.johnsmith.SpringBootInstagramApi.payloads.requests.LoginRequest;
import com.johnsmith.SpringBootInstagramApi.payloads.requests.RegisterRequest;

public interface AuthService {
	User register(RegisterRequest request);
	String login(LoginRequest request);
}
