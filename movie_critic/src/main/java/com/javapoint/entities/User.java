package com.javapoint.entities;

import java.util.*;
import javax.persistence.*;
import lombok.*;
@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;
	private String first_name;
	private String last_name;
	private String gender;
	private int age;
	private String contact_number;
	private String email;
	private String password;


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Collection<Role> roles = new HashSet<>();

	public User(int id, String first_name, String last_name, String gender, int age, String contact_number, String email, String password) {
		super();	
	}
	public User(int user_id,String first_name, String last_name, String gender, int age, String contact_number, String email,
			 String password, List<Role> roles) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.age = age;
		this.contact_number = contact_number;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	public User() {
		
	}
}