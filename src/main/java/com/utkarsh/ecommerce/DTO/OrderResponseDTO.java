package com.utkarsh.ecommerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private Long orderId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(
            Long orderId,
            String status,
            BigDecimal totalAmount,
            LocalDateTime createdAt,
            List<OrderItemResponseDTO> items
    ) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.items = items;
    }

    // Generate getters and setters for all fields.
}