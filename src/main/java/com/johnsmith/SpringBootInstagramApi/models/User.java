package com.johnsmith.SpringBootInstagramApi.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.johnsmith.SpringBootInstagramApi.payloads.responses.UserResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullname;
	private String username;
	private String password;
	private String website;
	private String mobile;
	private String gender;
	private String avatar;
	private String bio;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Story> stories = new HashSet<Story>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<Post>();
	
	@Enumerated
	@ElementCollection
	private Set<UserResponse> followers = new HashSet<UserResponse>();
	
	@Enumerated
	@ElementCollection
	private Set<UserResponse> followings = new HashSet<UserResponse>();
}













