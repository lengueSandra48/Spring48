package com.lenguecode.userhandler.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserDto {
    private String fullName;
    private String email;
    private List<String> roles;
    private Boolean isActive;
}