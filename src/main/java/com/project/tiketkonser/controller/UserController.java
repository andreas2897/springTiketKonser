package com.project.tiketkonser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tiketkonser.dao.UserRepo;
import com.project.tiketkonser.entity.Users;
import com.project.tiketkonser.util.EmailUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EmailUtil emailUtil;
	
	@GetMapping
	public Iterable<Users> getUser(){
		return userRepo.findAll();
	}
	//register user
	@PostMapping
	public Users registerUser(@RequestBody Users user) {
		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		Users savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		String link = "http://localhost:3000/verified?username=" + user.getusername();
		
		this.emailUtil.sendEmail(user.getEmail(), "User Registration", "Hi " + user.getusername() + " please kindly paste the link to verified your account " + link );
		
		return savedUser;
	}
	
	
	//login user
	@GetMapping("/login")
	public Users getLoginUser(@RequestParam String username, @RequestParam String password) {
		Users findUser = userRepo.findByUsername(username).get();

		if (pwEncoder.matches(password, findUser.getPassword())) {	
			findUser.setPassword(null);
			return findUser;
		} 

		throw new RuntimeException("Wrong password!");
	}
	
	@GetMapping("/username")
	public Iterable<Users> getUsername(@RequestParam String username){
		return userRepo.findUsername(username);
	}
	
	@GetMapping("/keeplogin")
	public Users keepLoginUser(@RequestParam int id) {
		Users findUser = userRepo.findById(id).get();
		
		return findUser;
	}
	
	
	
	
}
