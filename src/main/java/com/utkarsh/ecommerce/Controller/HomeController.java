package com.utkarsh.ecommerce.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public Map<String, Object> hello() {

        return Map.of(
                "message", "Welcome to E-Commerce Application!",
                "application", "ecommerce",
                "status", "success"
        );
    }
}