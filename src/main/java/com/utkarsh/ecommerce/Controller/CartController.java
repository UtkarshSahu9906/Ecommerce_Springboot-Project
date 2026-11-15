package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.Service.CartService;
import com.utkarsh.ecommerce.dto.AddToCartRequestDTO;
import com.utkarsh.ecommerce.dto.CartResponseDTO;
import com.utkarsh.ecommerce.dto.UpdateCartItemRequestDTO;
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

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequestDTO request,
            Authentication authentication) {

        CartResponseDTO response = cartService.updateCartItem(
                authentication.getName(),
                productId,
                request.getQuantity()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            Authentication authentication) {

        cartService.clearCart(authentication.getName());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeCartItem(
            @PathVariable Long productId,
            Authentication authentication) {

        CartResponseDTO response = cartService.removeCartItem(
                authentication.getName(),
                productId
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(
            Authentication authentication) {

        String email = authentication.getName();

        CartResponseDTO response = cartService.getCart(email);

        return ResponseEntity.ok(response);
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