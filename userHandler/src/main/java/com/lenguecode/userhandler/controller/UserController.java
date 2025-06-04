package com.lenguecode.userhandler.controller;

import com.lenguecode.userhandler.dtos.UpdateUserDto;
import com.lenguecode.userhandler.entities.User;
import com.lenguecode.userhandler.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Add this at class level
public class UserController {
    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(service.getUserList());
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = service.updateUser(id, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
