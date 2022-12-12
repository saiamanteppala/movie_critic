package com.javapoint.service;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.entities.User;

public interface RegistrationService {
	User save(UserRegistrationDto userRegistrationDto);
}
