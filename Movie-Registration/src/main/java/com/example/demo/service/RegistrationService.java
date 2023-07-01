package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Registration;
import com.example.demo.entity.UserDto;

public interface RegistrationService {

	Registration findByEmail(String email);

	void saveUser(UserDto user);

	List<UserDto> findAllUsers();

}
