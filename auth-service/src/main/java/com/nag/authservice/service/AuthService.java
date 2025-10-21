package com.nag.authservice.service;

import com.nag.authservice.dto.LoginRequestDTO;
import com.nag.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u-> jwtUtil.generateToken(u.getEmail(),u.getRole()));
        return token;
    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException exception){
            return false;
        }
    }
}
