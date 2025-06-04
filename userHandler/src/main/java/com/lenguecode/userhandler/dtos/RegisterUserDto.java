package com.lenguecode.userhandler.dtos;

import com.lenguecode.userhandler.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
    private List<String> roles;
}
