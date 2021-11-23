package com.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "users", 
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Email
	private String email;
	private String password;
	private String fullname;
	private String DOB;
	private String image;
	private boolean isBlacklisted;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "role", referencedColumnName = "roleId")
	private Role role;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription", referencedColumnName = "subscriptionId")
	private Subscription subscription;

	public User(String email, String password, String fullname, String DOB, String image, boolean isBlacklisted,Subscription subscription) {
		this.email=email;
		this.password=password;
		this.fullname=fullname;
		this.DOB=DOB;
		this.image=image;
		this.isBlacklisted=isBlacklisted;
		this.subscription=subscription;
	}

	/*
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();*/
}
