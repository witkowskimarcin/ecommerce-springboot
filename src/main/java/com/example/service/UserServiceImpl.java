package com.example.service;

import com.example.model.UserModel;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{

    private UserRepository userRepository;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, Mappers mappers)
    {
        this.userRepository = userRepository;
        this.mappers = mappers;
    }

    @Override
    public UserModel findByUsername(String username)
    {
        return mappers.mapUserEntityToModel(userRepository.findByUsername(username).orElseThrow(
                ()->new RuntimeException("User username: "+username+" does not exist")));
    }

    @Override
    public UserModel findByEmail(String email)
    {
        return mappers.mapUserEntityToModel(userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User email: "+email+" does not exist")));
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
    public UserModel getCurrentUser()
    {
        return findByUsername(getCurrentUserName());
    }
}
