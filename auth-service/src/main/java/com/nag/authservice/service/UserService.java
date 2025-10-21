package com.nag.authservice.service;

import com.nag.authservice.UserRepository;
import com.nag.authservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){


        return userRepository.findByEmail(email);
    }
}
