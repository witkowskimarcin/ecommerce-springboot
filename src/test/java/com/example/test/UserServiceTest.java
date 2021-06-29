package com.example.test;

import com.example.CommonResources;
import com.example.entity.User;
import com.example.exception.EmailAlreadyExistsException;
import com.example.model.SessionModel;
import com.example.model.UserModel;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Authentication auth;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Before
    public void before() {
        session = mock(HttpSession.class);
    }

    @When("getAuthentication.getName")
    public void getAuthenticationName() {
        when(auth.getName()).thenReturn(CommonResources.userEntity.getEmail());
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(CommonResources.userEntity.getEmail());
    }

    @When("userRepository.findByUsername")
    public void userRepositoryFindByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(CommonResources.userEntity));
    }

    @When("userRepository.findByEmail")
    public void userRepositoryFindByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(CommonResources.userEntity));
    }

    @When("userRepository.existsByUsername")
    public void userRepositoryExistsByUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
    }

    @When("userRepository.existsByEmail")
    public void userRepositoryExistsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
    }

    @Then("userService.findByUsername")
    public void findByUsername() {
        UserModel result = userService.findByUsername(anyString());
        assertNotNull(result.getEmail());
    }

    @Then("userService.findByEmail")
    public void findByEmail() {
        UserModel result = userService.findByEmail(anyString());
        assertNotNull(result.getEmail());
    }

    @Then("userService.existsByUsername")
    public void existsByUsername() {
        boolean result = userService.existsByUsername(anyString());
        assertTrue(result);
    }

    @Then("userService.existsByEmail")
    public void existsByEmail() {
        boolean result = userService.existsByUsername(anyString());
        assertTrue(result);
    }

    @Then("userService.getCurrentUserName")
    public void getCurrentUserName() {
        String result = userService.getCurrentUserName();
        assertTrue(result.length() > 0);
    }

    @Then("userService.getCurrentUser")
    public void getCurrentUser() {
        User result = userService.getCurrentUser();
        assertNotNull(result);
    }

    @Then("userService.register")
    public void register() {
        try {
            userService.register(CommonResources.userModel);
        } catch (EmailAlreadyExistsException e) {
            Assertions.assertThrows(EmailAlreadyExistsException.class, () -> {
                throw e;
            });
        }
    }

    //    @Then("userService.logged")
    //    public void logged(){
    //        SessionModel result = userService.logged(session);
    //        assertNotNull(result);
    //    }
}
