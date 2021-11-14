package com.system.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

	private String role;

}