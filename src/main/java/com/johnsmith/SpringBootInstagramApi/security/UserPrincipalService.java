package com.johnsmith.SpringBootInstagramApi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.johnsmith.SpringBootInstagramApi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username).map(UserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException("Unauthorized!"));
	}

}
