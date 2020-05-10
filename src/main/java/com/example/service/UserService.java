package com.example.service;

import com.example.entity.User;
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
    UserModel getCurrentUserModel();
    User getCurrentUser();
    SessionModel logged(HttpSession session);
    void register(UserModel user);
    JwtResponse authenticateUser(LoginForm loginRequest);
    void removeUserById(Long id);
}
