package com.johnsmith.SpringBootInstagramApi.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.johnsmith.SpringBootInstagramApi.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails {
	private String fullname;
	private String username;
	private String password;
	private String avatar;
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
	
	public UserPrincipal(User user) {
		this.fullname = user.getFullname();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.avatar = user.getAvatar();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
