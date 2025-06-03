package com.lenguecode.userhandler;

import com.lenguecode.userhandler.entities.Role;
import com.lenguecode.userhandler.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserHandlerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository){

        return args -> {

            if (roleRepository.findByRoleName("USER").isEmpty()){

                roleRepository.save(
                        Role.builder().roleName("USER").build()
                );

            }
            if (roleRepository.findByRoleName("ADMIN").isEmpty()){

                roleRepository.save(
                        Role.builder().roleName("ADMIN").build()
                );

            }
        };
    }

}
