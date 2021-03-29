package com.stackroute.userservice.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.userservice.exceptions.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.SecurityTokenGenerator;
import com.stackroute.userservice.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping(path="/api/v1/userauthservice")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityTokenGenerator securityTokenGenerator;

//	@Autowired
//	public UserController(UserService userservice, SecurityTokenGenerator securityTokenGenerator) {
//		this.userService = userservice;
//		this.securityTokenGenerator = securityTokenGenerator;
//	}

	
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return new ResponseEntity<>("user Registered sucessfully", HttpStatus.CREATED);			
		}catch (Exception e) {
			return new ResponseEntity<String>("{ \"message\":\""+e.getMessage(), HttpStatus.CONFLICT);
}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {

		Map<String, String> map = null;
		try {

			if (user.getUserId() ==  null || user.getPassword() == null){
				throw new Exception("User or password Cannot be empty");
			}
			
			User userObj = userService.findByUsernameAndPassword(user.getUserId(), user.getPassword());

			if (userObj == null){
				throw new Exception ("User with given ID doesnot exists");
			}
			
			if (!user.getPassword().equals(userObj.getPassword())){
				throw new Exception("Invalid Credentials. Please check username and password");

			}
			
			if (userObj.getUserId().equals(user.getUserId())) {
				map = securityTokenGenerator.generateToken(user);
			}
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Try After Sometime !!", HttpStatus.UNAUTHORIZED);
		}


	}
}
