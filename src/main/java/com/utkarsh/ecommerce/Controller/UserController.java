package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.Service.UserService;
import com.utkarsh.ecommerce.DTO.UserRegistrationRequestDTO;
import com.utkarsh.ecommerce.DTO.UserResponseDTO;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(
            @Valid @RequestBody UserRegistrationRequestDTO requestDTO) {

        return userService.registerUser(requestDTO);
    }
}