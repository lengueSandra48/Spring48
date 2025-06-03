package com.lenguecode.userhandler.service;

import com.lenguecode.userhandler.controller.dtos.LoginUserDto;
import com.lenguecode.userhandler.controller.dtos.RegisterUserDto;
import com.lenguecode.userhandler.entities.User;
import com.lenguecode.userhandler.repository.RoleRepository;
import com.lenguecode.userhandler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input){

        //try to get the user role
            var userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        //Create user object and persists in database
        User user = User.builder()
                .username(input.getUsername())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword())) //encode the user password
                .isActive(false) //by default the account is not active
                .roles(List.of(userRole))
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
       return userRepository.findByEmail(input.getEmail()).orElseThrow();


    }
}
