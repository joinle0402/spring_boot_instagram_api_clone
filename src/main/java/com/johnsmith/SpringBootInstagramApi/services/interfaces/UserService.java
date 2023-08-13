package com.johnsmith.SpringBootInstagramApi.services.interfaces;

import java.util.List;

import com.johnsmith.SpringBootInstagramApi.models.User;

public interface UserService {
	List<User> findAll();
	List<User> findByQuery(String query);
	User findById(Long id);
	User findByUsername(String username);
	String followUser(Long userId, Long followerId);
	String unFollowUser(Long userId, Long followerId);
}
