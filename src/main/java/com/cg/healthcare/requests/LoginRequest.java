package com.cg.healthcare.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {
	
	@NotNull(message="Username cannot be Omitted")
	@NotEmpty(message="Username cannot be left blank")
	@Email(message = "Username must be a valid email")
	private String username;
	
	@NotNull(message="Password cannot be Omitted")
	@NotEmpty(message="Password cannot be left blank")
	private String password;
	
	
	
	public LoginRequest(
			@NotNull(message = "Username cannot be Omitted") @NotEmpty(message = "Username cannot be left blank") 
			@Email(message = "Username must be a valid email") String username,
			@NotNull(message = "Password cannot be Omitted") @NotEmpty(message = "Password cannot be left blank") String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
