package com.javapoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.service.UserService;
@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class LoginController {

	@Autowired
	public UserService userService;

	@PostMapping("/login")
	public String loginValid(@RequestBody UserRegistrationDto userDto) throws Exception
	{
		if (userService.ValidateUserLogin(userDto))
			return "Login Successful";
		else
			return "Invalid Credentials";
	}

}