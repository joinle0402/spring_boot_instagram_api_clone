package com.johnsmith.SpringBootInstagramApi.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String fullname;
	private String username;
	private String avatar;
}
