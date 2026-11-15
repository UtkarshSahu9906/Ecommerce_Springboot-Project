package com.utkarsh.ecommerce.Service;

import com.utkarsh.ecommerce.DTO.OrderItemResponseDTO;
import com.utkarsh.ecommerce.Repository.OrderRepository;
import com.utkarsh.ecommerce.Repository.UserRepository;
import com.utkarsh.ecommerce.Repository.CartRepository;
import com.utkarsh.ecommerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.utkarsh.ecommerce.Entity.*;
import com.utkarsh.ecommerce.DTO.OrderResponseDTO;
import com.utkarsh.ecommerce.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {

        List<OrderItemResponseDTO> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(
                                BigDecimal.valueOf(item.getQuantity())
                        )
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items
        );
    }

    @Transactional
    public OrderResponseDTO cancelOrder(String email, Long orderId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository.findByIdAndUserId(
                orderId, user.getId()
        ).orElseThrow(() ->
                new ResourceNotFoundException("Order not found"));

        // Only placed orders can be cancelled
        if (!order.getStatus().equals("PLACED")) {
            throw new IllegalArgumentException(
                    "Only placed orders can be cancelled"
            );
        }

        // Restore product stock
        for (OrderItem item : order.getItems()) {

            Product product = item.getProduct();

            product.setStock(
                    product.getStock() + item.getQuantity()
            );

            productRepository.save(product);
        }

        // Update order status
        order.setStatus("CANCELLED");

        Order updatedOrder = orderRepository.save(order);

        return convertToOrderResponseDTO(updatedOrder);
    }
    @Transactional
    public List<OrderResponseDTO> getMyOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Order> orders =
                orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .toList();
    }
    @Transactional
    public OrderResponseDTO getMyOrderById(String email, Long orderId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return convertToOrderResponseDTO(order);
    }
    @Transactional
    public OrderResponseDTO placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(
                    cartItem.getProduct().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));

            if (cartItem.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getName()
                );
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(
                    cartItem.getProduct().getId()
            ).orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());

            // Save purchase-time price
            orderItem.setPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            totalAmount = totalAmount.add(subtotal);

            product.setStock(product.getStock() - cartItem.getQuantity());

            order.getItems().add(orderItem);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return convertToOrderResponseDTO(savedOrder);
    }
}