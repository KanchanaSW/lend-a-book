package com.system.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String fullname;
	private String email;
	private String DOB;
	private String signupDate;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String fullname, String email,String DOB,String signupDate, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.DOB=DOB;
		this.signupDate=signupDate;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}


	public String getDOB() {
		return DOB;
	}



	public String getSignupDate() {
		return signupDate;
	}

		public List<String> getRoles() {
		return roles;
	}
}
