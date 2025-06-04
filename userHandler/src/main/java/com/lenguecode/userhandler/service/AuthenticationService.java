package com.lenguecode.userhandler.service;

import com.lenguecode.userhandler.dtos.LoginUserDto;
import com.lenguecode.userhandler.dtos.RegisterUserDto;
import com.lenguecode.userhandler.entities.Role;
import com.lenguecode.userhandler.entities.User;
import com.lenguecode.userhandler.repository.RoleRepository;
import com.lenguecode.userhandler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User signup(RegisterUserDto input){

        //try to get the user roles
        List<?> roleNames = input.getRoles() != null && !input.getRoles().isEmpty()
                ? input.getRoles()
                : List.of("USER");

        // Get all roles (throws exception if any role not found)
        List<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByRoleName((String) roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role " + roleName + " not found")))
                .collect(Collectors.toList());

        User user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword())) //encode the user password
                .isActive(false) //by default the account is not active
                .roles(roles)
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
