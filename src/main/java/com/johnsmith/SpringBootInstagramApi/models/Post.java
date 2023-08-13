package com.johnsmith.SpringBootInstagramApi.models;

import java.time.LocalDateTime;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String caption;
	private String image;
	private LocalDateTime createdAt;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<Comment>();
	
	@Enumerated
	@ElementCollection
	@JoinTable(name = "likedByUsers", joinColumns = @JoinColumn(columnDefinition = "user_id"))
	private Set<User> likedByUsers = new HashSet<User>();
	
}
