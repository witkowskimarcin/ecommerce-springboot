package com.example.service;

import com.example.message.JwtResponse;
import com.example.model.LoginForm;
import com.example.model.SessionModel;
import com.example.model.UserModel;

import javax.servlet.http.HttpSession;


public interface UserService
{
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    String getCurrentUserName();
    UserModel getCurrentUser();
    SessionModel logged(HttpSession session);
    String getSessionId();
    void register(UserModel user);
    JwtResponse authenticateUser(LoginForm loginRequest);
}
