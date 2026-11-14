package com.utkarsh.ecommerce.Service;

import com.utkarsh.ecommerce.Entity.User;
import com.utkarsh.ecommerce.Repository.UserRepository;
import com.utkarsh.ecommerce.DTO.UserRegistrationRequestDTO;
import com.utkarsh.ecommerce.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegistrationRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());

        user.setPassword(
                passwordEncoder.encode(requestDTO.getPassword())
        );

        user.setRole("USER");

        return userRepository.save(user);
    }
}