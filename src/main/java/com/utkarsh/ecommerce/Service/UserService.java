package com.utkarsh.ecommerce.Service;

import com.utkarsh.ecommerce.DTO.UserResponseDTO;
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
    public UserResponseDTO registerUser(
            UserRegistrationRequestDTO requestDTO) {

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

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setRole(savedUser.getRole());

        return responseDTO;
    }

}