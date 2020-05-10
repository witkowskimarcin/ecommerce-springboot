package com.example;

import com.example.entity.User;
import com.example.exception.ResourceNotFoundException;
import com.example.model.UserModel;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtProvider;
import com.example.service.SessionService;
import com.example.service.Mappers;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest
{
    @MockBean private UserRepository userRepository;
    @MockBean private SessionService sessionService;
    @MockBean private Mappers mappers;
    @MockBean private RoleRepository roleRepository;
    @MockBean private PasswordEncoder encoder;
    @MockBean private AuthenticationManager authenticationManager;
    @MockBean private JwtProvider jwtProvider;
    @MockBean private InitEntryData initEntryData;
//    @MockBean private SecurityContextHolder securityContextHolder;
    @Autowired private UserService userService;

    private static final String TEST_USERNAME = "TestUser";

    @Test
    public void findByUsername(){
//        User u = new User();
//        u.setEmail(TEST_USERNAME);
//        when(userRepository.findByEmail(TEST_USERNAME).orElseThrow(
//                ()->new ResourceNotFoundException("User username: "+TEST_USERNAME+" does not exist"))).thenReturn(u);
//       UserModel result = userService.findByEmail(TEST_USERNAME);
//       assertEquals(result.getEmail(), TEST_USERNAME);
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
//        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(TEST_USERNAME);
//        User u = new User();
//        u.setEmail(TEST_USERNAME);
////        when(userRepository.findByUsername(TEST_USERNAME).orElseThrow(
////                ()->new ResourceNotFoundException("User username: "+TEST_USERNAME+" does not exist"))).thenReturn(u);
//        assertEquals(userService.getCurrentUserName(), TEST_USERNAME);
////        assertEquals(TEST_USERNAME, TEST_USERNAME);
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
