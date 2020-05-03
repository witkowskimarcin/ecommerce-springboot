package com.example;

import com.example.message.JwtResponse;
import com.example.model.LoginForm;
import com.example.model.SessionModel;
import com.example.model.UserModel;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtProvider;
import com.example.service.CartService;
import com.example.service.Mappers;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest
{
    @MockBean private UserRepository userRepository;
    @MockBean private CartService cartService;
    @MockBean private HttpSession session;
    @MockBean private Mappers mappers;
    @MockBean private RoleRepository roleRepository;
    @MockBean private PasswordEncoder encoder;
    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private JwtProvider jwtProvider;

    @Autowired private UserService userService;

    @Test
    public void  findByUsername(){
        // TODO
    }

    @Test
    public void  findByEmail(){
        // TODO
    }

    @Test
    public void  existsByUsername(){
        // TODO
    }

    @Test
    public void  existsByEmail(){
        // TODO
    }

    @Test
    public void  getCurrentUserName(){
        // TODO
    }

    @Test
    public void  getCurrentUser(){
        // TODO
    }

    @Test
    public void  logged(){
        // TODO
    }

    @Test
    public void  getSessionId(){
        // TODO
    }

    @Test
    public void  register(){
        // TODO
    }

    @Test
    public void  authenticateUser(){
        // TODO
    }
}
