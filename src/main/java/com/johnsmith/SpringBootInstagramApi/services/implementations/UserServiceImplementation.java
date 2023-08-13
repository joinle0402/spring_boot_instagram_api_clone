package com.johnsmith.SpringBootInstagramApi.services.implementations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.johnsmith.SpringBootInstagramApi.exceptions.ApiException;
import com.johnsmith.SpringBootInstagramApi.models.User;
import com.johnsmith.SpringBootInstagramApi.payloads.responses.UserResponse;
import com.johnsmith.SpringBootInstagramApi.repositories.UserRepository;
import com.johnsmith.SpringBootInstagramApi.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
	private final UserRepository userRepository;
	
	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User id not found!"));
	}

	@Override
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Username not found!"));
	}

	@Override
	public List<User> findByQuery(String query) {
		return this.userRepository.findByQuery(query);
	}

	@Override
	public String followUser(Long userId, Long followerId) {
		User requestUser = this.findById(userId);
		User followerUser = this.findById(followerId);
		
		UserResponse follower = new UserResponse();  
		follower.setId(requestUser.getId());
		follower.setFullname(requestUser.getFullname());
		follower.setUsername(requestUser.getUsername());
		follower.setAvatar(requestUser.getAvatar());
		
		UserResponse following = new UserResponse();  
		following.setId(followerUser.getId());
		following.setFullname(followerUser.getFullname());
		following.setUsername(followerUser.getUsername());
		following.setAvatar(followerUser.getAvatar());
		
		requestUser.getFollowings().add(following);
		followerUser.getFollowers().add(follower);
		
		this.userRepository.save(requestUser);
		this.userRepository.save(followerUser);
		
		
		return "You are following " + followerUser.getUsername();
	}

	@Override
	public String unFollowUser(Long userId, Long followerId) {
		User requestUser = this.findById(userId);
		User followerUser = this.findById(followerId);
		
		UserResponse follower = new UserResponse();  
		follower.setId(requestUser.getId());
		follower.setFullname(requestUser.getFullname());
		follower.setUsername(requestUser.getUsername());
		follower.setAvatar(requestUser.getAvatar());
		
		UserResponse following = new UserResponse();  
		following.setId(followerUser.getId());
		following.setFullname(followerUser.getFullname());
		following.setUsername(followerUser.getUsername());
		following.setAvatar(followerUser.getAvatar());
		
		requestUser.getFollowings().remove(following);
		followerUser.getFollowers().remove(follower);
		
		this.userRepository.save(requestUser);
		this.userRepository.save(followerUser);
		
		
		return "You have unfollowed " + followerUser.getUsername();
	}

}
