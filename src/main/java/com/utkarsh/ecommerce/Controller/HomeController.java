package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.DTO.HomeResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/hello")
  public HomeResponseDTO hello(){
        return new HomeResponseDTO(
                "Welcome to E-Commerce Application!",
                "ecommerce",
                "success");
    }
}