package com.lenguecode.userhandler.controller.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
