package com.javapoint.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id;
	
	private String role_name;
		
	public Role(String role_name) {
		super();
		this.role_name = role_name;
	}
	public Role() {
		
	}
	
}
