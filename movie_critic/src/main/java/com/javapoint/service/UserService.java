package com.javapoint.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javapoint.controller.dto.UserRegistrationDto;
import com.javapoint.entities.User;
import com.javapoint.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	public User saveUserDetails(User user) {
		return userRepo.save(user);
	}
	public List<User> getAllUserDetails() {
		return (List<User>) userRepo.findAll();
	}
	public User getUserDetailsById(int id) {
		return userRepo.findById(id);
	}
	public void deleteUserById(int id) {
		userRepo.deleteById(id);
	}
	public User updateUserDetails(User user) {
		return userRepo.save(user);
	}
	public void deleteUser(User user) {
		userRepo.delete(user);
	}
	
	public boolean ValidateUserLogin(UserRegistrationDto userDto) {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(userDto.getEmail());
		if (user == null) {
			System.out.println("User not found");
			return false;
		}
		else {
			if (user.getPassword().equals(userDto.getPassword())) {
				System.out.println("Login Successful");
				return true;
			}
			else {
				System.out.println("Invalid Credentials");
				return false;
			}
		}
	}
}
