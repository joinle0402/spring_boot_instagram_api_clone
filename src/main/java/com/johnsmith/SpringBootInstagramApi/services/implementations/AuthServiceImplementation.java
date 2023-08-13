package com.johnsmith.SpringBootInstagramApi.services.implementations;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johnsmith.SpringBootInstagramApi.exceptions.ApiException;
import com.johnsmith.SpringBootInstagramApi.models.User;
import com.johnsmith.SpringBootInstagramApi.payloads.requests.LoginRequest;
import com.johnsmith.SpringBootInstagramApi.payloads.requests.RegisterRequest;
import com.johnsmith.SpringBootInstagramApi.repositories.UserRepository;
import com.johnsmith.SpringBootInstagramApi.security.JwtUtils;
import com.johnsmith.SpringBootInstagramApi.security.UserPrincipal;
import com.johnsmith.SpringBootInstagramApi.services.interfaces.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Override
	public User register(RegisterRequest request) {
		Optional<User> existingUser = this.userRepository.findByUsername(request.getUsername());
		if (existingUser.isPresent()) {
			throw new ApiException(HttpStatus.CONFLICT, "Username already exists!");
		}
		User user = new User();
		user.setFullname(request.getFullname());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		return this.userRepository.save(user);
	}

	@Override
	public String login(LoginRequest request) {
		Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = this.jwtUtils.generateToken(userPrincipal);
		return accessToken;
	}

}
