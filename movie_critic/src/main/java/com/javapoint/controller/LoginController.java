package com.javapoint.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.service.UserService;

@Controller
@RequestMapping("/user")
public class LoginController {

	@Autowired
	public UserService userService;

	@GetMapping("/login")
	public String loginValid(@RequestBody UserRegistrationDto userDto) throws Exception
	{
		if (userService.ValidateUserLogin(userDto))
			return "Login Successful";
		else
			return "Invalid Credentials";
	}

}