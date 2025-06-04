package com.lenguecode.userhandler.service;

import com.lenguecode.userhandler.dtos.UpdateUserDto;
import com.lenguecode.userhandler.entities.Role;
import com.lenguecode.userhandler.entities.User;
import com.lenguecode.userhandler.repository.RoleRepository;
import com.lenguecode.userhandler.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (updateUserDto.getFullName() != null) {
            user.setFullName(updateUserDto.getFullName());
        }

        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getRoles() != null) {
            List<Role> roles = updateUserDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }

        if (updateUserDto.getIsActive() != null) {
            user.setActive(updateUserDto.getIsActive());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }
}