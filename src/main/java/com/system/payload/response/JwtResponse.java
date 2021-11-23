package com.system.payload.response;

import com.system.models.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private Long id;
	private String fullname;
	private String email;
	private Date expiretime;
	private String password;
	private boolean isBlacklisted;
	private String roles;
	private String subType;

	public JwtResponse(String accessToken, Long id, String fullname, String email,
					   String password,
					   boolean isBlacklisted, String role, Date expiretime,String subType) {
		this.accessToken=accessToken;
		this.id=id;
		this.fullname=fullname;
		this.email=email;
		this.password=password;
		this.isBlacklisted=isBlacklisted;
		this.roles=role;
		this.expiretime=expiretime;
		this.subType=subType;
	}
}