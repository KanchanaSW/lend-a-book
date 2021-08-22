package com.system.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String fullname;

	private String email;

	@JsonIgnore
	private String password;

	private String DOB;

	private String signupDate;

	private String image;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String fullname, String email, String password,String DOB,String signupDate,String image,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.DOB=DOB;
		this.signupDate=signupDate;
		this.image=image;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getFullname(),
				user.getEmail(),
				user.getPassword(),
				user.getDOB(),
				user.getSignupDate(),
				user.getImage(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return null;
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

	public String getImage() {
		return image;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
