package com.example.service;

import com.example.entity.Role;
import com.example.entity.RoleName;
import com.example.entity.User;
import com.example.exception.EmailAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.message.JwtResponse;
import com.example.model.LoginForm;
import com.example.model.SessionModel;
import com.example.model.UserModel;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;
    private SessionService sessionService;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, SessionService sessionService, Mappers mappers, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.mappers = mappers;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserModel findByUsername(String username)
    {
        return mappers.mapUserEntityToModel(userRepository.findByUsername(username).orElseThrow(
                ()->new ResourceNotFoundException("User username: "+username+" does not exist")));
    }

    @Override
    public UserModel findByEmail(String email)
    {
        return mappers.mapUserEntityToModel(userRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("User email: "+email+" does not exist")));
    }

    @Override
    public Boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email)
    {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String getCurrentUserName()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public UserModel getCurrentUserModel()
    {
        return findByUsername(getCurrentUserName());
    }

    @Override
    public User getCurrentUser() {
        return (userRepository.findByUsername(getCurrentUserName()).orElseThrow(
                ()->new ResourceNotFoundException("User username: "+getCurrentUserName()+" does not exist")));
    }

    @Override
    public SessionModel logged(HttpSession session)
    {
        SessionModel sessionModel = new SessionModel();
        UserModel currentUser = getCurrentUserModel();
        sessionModel.setUser(currentUser);
        sessionModel.setCartQuantity(sessionService.getQuantity());
        return sessionModel;
    }

    @Override
    public void register(UserModel userModel)
    {
        if (userRepository.existsByEmail(userModel.getEmail())) throw new EmailAlreadyExistsException(userModel.getEmail());
        
        User user = mappers.mapUserModelToEntity(userModel);
        user.setActive(1);
        user.setPassword(encoder.encode(userModel.getPassword()));
        
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        logger.info("Register user successfully");
    }

    @Override
    public JwtResponse authenticateUser(LoginForm loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }

    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
        logger.info("User has been removed successfully");
    }
}
