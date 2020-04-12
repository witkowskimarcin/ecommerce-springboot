package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.CartModel;
import com.example.model.SessionModel;
import com.example.model.UserModel;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService
{

    private UserRepository userRepository;
    private HttpSession session;
    private Mappers mappers;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, HttpSession session, Mappers mappers)
    {
        this.userRepository = userRepository;
        this.session = session;
        this.mappers = mappers;
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
    public UserModel getCurrentUser()
    {
        return findByUsername(getCurrentUserName());
    }

    @Override
    public SessionModel logged(HttpSession session)
    {
        SessionModel sessionModel = new SessionModel();
        UserModel currentUser = getCurrentUser();
        CartModel cartModel = CartModel.getCartInSession(session);
        sessionModel.setUser(currentUser);
        sessionModel.setCartQuantity(cartModel.getQuantity());
        return sessionModel;
    }

    @Override
    public String getSessionId()
    {
        return session.getId();
    }
}
