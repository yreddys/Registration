package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Registration;
import com.example.demo.entity.UserDto;
import com.example.demo.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService{
@Autowired
private RegistrationRepository Repo;

@Override
public Registration findByEmail(String email) {
	
	 
	return Repo.findByEmail(email);
}

@Override
public void saveUser(UserDto userDto) {
	Registration user = new Registration();
     user.setName(userDto.getName());
     user.setEmail(userDto.getEmail());
     user.setPassword(userDto.getPassword());
     Repo.save(user);
     
     
     }

@Override
public List<UserDto> findAllUsers() {
	
	List<Registration> users = Repo.findAll();
    return users.stream().map((user) -> convertEntityToDto(user))
            .collect(Collectors.toList());
}

private UserDto convertEntityToDto(Registration user){
    UserDto userDto = new UserDto();
    String[] name = user.getName().split(" ");
    userDto.setName(name[0]);
    
    userDto.setEmail(user.getEmail());
    return userDto;
}
}
     
     
 
	



