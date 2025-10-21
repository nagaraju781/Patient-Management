package com.nag.authservice.controller;

import com.nag.authservice.dto.LoginRequestDTO;
import com.nag.authservice.dto.LoginResponseDTO;
import com.nag.authservice.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Optional<String> token = authService.authenticate(loginRequestDTO);

        if(token.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String tokenValue = token.get();
        return ResponseEntity.ok(new LoginResponseDTO(tokenValue));
    }
    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String authHeader) {

        System.out.println(authHeader);
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
