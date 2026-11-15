package com.utkarsh.ecommerce.DTO;

import java.math.BigDecimal;

public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    public OrderItemResponseDTO() {
    }

    public OrderItemResponseDTO(
            Long productId,
            String productName,
            Integer quantity,
            BigDecimal price,
            BigDecimal subtotal
    ) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    // Generate getters and setters for all fields.
}