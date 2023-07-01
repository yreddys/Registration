package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Registration;
import com.example.demo.entity.UserDto;
import com.example.demo.service.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class RegistrationController {
@Autowired
private RegistrationService service;

@GetMapping("index")
public String home(){
    return "index";
}

@GetMapping("/login")
public String loginForm() {
 return "login";
//	return "redirect:/index";
}

@GetMapping("register")
public String showRegistrationForm(Model model){
    UserDto user = new UserDto();
    model.addAttribute("user", user);
    return "register";
}
// handler method to handle register user form submit request
@PostMapping("/register/save")
public String registration( @ModelAttribute("user") UserDto user,
                           BindingResult result,
                           Model model){
    Registration existing = service.findByEmail(user.getEmail());
    if (existing != null) {
        result.rejectValue("email", null, "There is already an account registered with that email");
    }
    if (result.hasErrors()) {
        model.addAttribute("user", user);
        return "register";
    }
    service.saveUser(user);
    return "redirect:/register?success";
}

@GetMapping("/users")
public String listRegisteredUsers(Model model){
    List<UserDto> users = service.findAllUsers();
    model.addAttribute("users", users);
    return "users";
}

@GetMapping("/logout")
public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
    // Invalidate user session
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    
    // Clear any authentication-related cookies or tokens
    
    // Redirect to the login page or any other desired page
//    return "redirect:/login?logout";
    return "redirect:/index";
}
//@PostMapping("/login")
//public String login(@RequestParam String email, @RequestParam String password, Model model) {
//    // Perform authentication logic here
//	Registration registration = new Registration();
//	registration=service.findByEmail(email);
//	if (email.equals(registration.getEmail())) {
//    // If authentication is successful, redirect to the home page or any other desired page
//    return "redirect:/users";
//	}
//	else {
//    // If authentication fails, add an error message to the model and return the login form view
//    // model.addAttribute("error", "Invalid email or password");
//    // return "login";
//		model.addAttribute("error", "Invalid email or password");
//        return "redirect:/login";
//    }

@PostMapping("/login")
public String login(@RequestParam String email, @RequestParam String password, Model model) {
    // Perform authentication logic here
    Registration registration = service.findByEmail(email);
    
    if (registration != null) {
        // Registered email found in the database
        // Perform password validation here
        
        if (password.equals(registration.getPassword())) {
            // Authentication is successful, redirect to the home page or any other desired page
            return "redirect:/users";
        } else {
            // Password is incorrect, add an error message to the model and return the login form view
            model.addAttribute("error", "Invalid email or password");
           
            return "login";
        }
    } else {
        // Email is not registered or registration is null
        // Add an error message to the model and return the login form view
        model.addAttribute("error", "Invalid email or password");
        model.addAttribute("email", "please enter registered email");
        return "login";
        
    }
}

}


