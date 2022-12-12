package com.javapoint.serviceImpl;
import java.util.Arrays;
import org.springframework.stereotype.Service;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.entities.Role;
import com.javapoint.entities.User;
import com.javapoint.repository.UserRepo;
import com.javapoint.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private UserRepo userRepo;

	public RegistrationServiceImpl(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	@Override
	public User save(UserRegistrationDto userRegistrationDto) 
	{
		User user = new User(userRegistrationDto.getFirst_name(), 
							 userRegistrationDto.getLast_name(),
							 userRegistrationDto.getGender(), 
							 userRegistrationDto.getAge(), 
							 userRegistrationDto.getContact(),
							 userRegistrationDto.getEmail(), 
							 userRegistrationDto.getUser_name(),
							 Arrays.asList(new Role("ROLE_USER")));
		return userRepo.save(user);
	}
}