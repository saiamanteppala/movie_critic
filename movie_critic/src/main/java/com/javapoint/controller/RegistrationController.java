package com.javapoint.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.service.RegistrationService;
@CrossOrigin("*")
@RestController
@RequestMapping("/registration")
public class RegistrationController {

	private RegistrationService registrationService;

	public RegistrationController(RegistrationService registrationService) 
	{
		super();
		this.registrationService = registrationService; 
	}
	@GetMapping
	public String showRegistrationForm(Model model)
	{
		model.addAttribute("user", new UserRegistrationDto());
		return "registration";
	}
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) 
	{
		registrationService.save(userRegistrationDto);
		return "redirect:/registration?sucess";
	}
} 
