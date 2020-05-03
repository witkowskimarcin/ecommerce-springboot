package com.example.controller;

import javax.validation.Valid;

import com.example.model.UserModel;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.model.LoginForm;
import com.example.message.ResponseMessage;
import com.example.security.jwt.JwtProvider;

//@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController
{
	private AuthenticationManager authenticationManager;
	private JwtProvider jwtProvider;
	private UserService userService;

	public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService)
	{
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.userService = userService;
	}

	@PostMapping(value="/signin",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> authenticateUser(@Valid LoginForm loginRequest) {

		return ResponseEntity.ok(userService.authenticateUser(loginRequest));
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<?> logout() {

		SecurityContextHolder.clearContext();
		return new ResponseEntity<>(new ResponseMessage("Logout successfully!"), HttpStatus.OK);
	}

//	@GetMapping(value = "/login")
//	public ResponseEntity<?> login(){
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
//	}

	@PostMapping(value = "/signup",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> registerUser(@Valid UserModel user) {

		userService.register(user);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}
