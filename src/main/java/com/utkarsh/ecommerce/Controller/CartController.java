package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.Service.CartService;
import com.utkarsh.ecommerce.dto.AddToCartRequestDTO;
import com.utkarsh.ecommerce.dto.CartResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @Valid @RequestBody AddToCartRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        CartResponseDTO response =
                cartService.addToCart(email, request);

        return ResponseEntity.ok(response);
    }
}