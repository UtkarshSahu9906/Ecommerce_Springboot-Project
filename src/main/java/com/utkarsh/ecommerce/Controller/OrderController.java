package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.Service.OrderService;
import com.utkarsh.ecommerce.DTO.OrderResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place a new order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                orderService.placeOrder(email)
        );
    }

    // Get logged-in user's order history
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                orderService.getMyOrders(email)
        );
    }


    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                orderService.cancelOrder(email, orderId)
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getMyOrderById(
            @PathVariable Long orderId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                orderService.getMyOrderById(email, orderId)
        );
    }
}