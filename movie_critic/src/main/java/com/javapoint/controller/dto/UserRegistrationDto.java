package com.javapoint.controller.dto;

import lombok.Data;

@Data
public class UserRegistrationDto  {
	private String first_name;
	private String last_name;
	private String gender;
	private int age;
	private String contact;
	private String email;
	private String user_name;
	private String password;

	public UserRegistrationDto() {
		super();
	}

	public UserRegistrationDto(String first_name, String last_name, String gender, int age, String contact,
			String email, String user_name, String password) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.age = age;
		this.contact = contact;
		this.email = email;
		this.user_name = user_name;
		this.password = password;
	}
}
