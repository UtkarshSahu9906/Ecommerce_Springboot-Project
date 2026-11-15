package com.utkarsh.ecommerce.Service;

import com.utkarsh.ecommerce.Entity.*;
import com.utkarsh.ecommerce.Repository.*;
import com.utkarsh.ecommerce.dto.AddToCartRequestDTO;
import com.utkarsh.ecommerce.dto.CartItemResponseDTO;
import com.utkarsh.ecommerce.dto.CartResponseDTO;
import com.utkarsh.ecommerce.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartResponseDTO removeCartItem(String email, Long productId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item ->
                        item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found in cart"));

        // Remove item from cart
        cart.getItems().remove(cartItem);

        cartRepository.save(cart);

        return convertToCartResponseDTO(cart);
    }

    @Transactional
    public CartResponseDTO updateCartItem(
            String email,
            Long productId,
            Integer quantity) {

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found in cart"));

        Product product = cartItem.getProduct();

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Insufficient product stock");
        }

        cartItem.setQuantity(quantity);

        cartRepository.save(cart);

        return convertToCartResponseDTO(cart);
    }

    private CartResponseDTO convertToCartResponseDTO(Cart cart) {

        List<CartItemResponseDTO> itemDTOs = cart.getItems()
                .stream()
                .map(item -> {

                    CartItemResponseDTO dto = new CartItemResponseDTO();

                    dto.setProductId(item.getProduct().getId());
                    dto.setProductName(item.getProduct().getName());
                    dto.setPrice(item.getProduct().getPrice());
                    dto.setQuantity(item.getQuantity());

                    BigDecimal subtotal = item.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

                    dto.setSubtotal(subtotal);

                    return dto;
                })
                .toList();

        BigDecimal totalPrice = itemDTOs.stream()
                .map(CartItemResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = itemDTOs.stream()
                .mapToInt(CartItemResponseDTO::getQuantity)
                .sum();

        CartResponseDTO response = new CartResponseDTO();

        response.setCartId(cart.getId());
        response.setItems(itemDTOs);
        response.setTotalItems(totalItems);
        response.setTotalPrice(totalPrice);

        return response;
    }
    @Transactional(readOnly = true)
    public CartResponseDTO getCart(String email) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Find user's cart
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        // Convert entity into DTO
        return convertToCartResponseDTO(cart);
    }

    @Transactional
    public CartResponseDTO addToCart(
            String email,
            AddToCartRequestDTO request) {

        // Find logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Find product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        // Find existing cart or create a new cart
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Find existing cart item
        CartItem existingItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId()
                        .equals(product.getId()))
                .findFirst()
                .orElse(null);

        int currentQuantity = existingItem == null
                ? 0 : existingItem.getQuantity();

        int newQuantity = currentQuantity + request.getQuantity();

        // Check available stock
        if (newQuantity > product.getStock()) {
            throw new IllegalArgumentException("Insufficient product stock");
        }

        // Update existing item or create a new item
        if (existingItem != null) {
            existingItem.setQuantity(newQuantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());

            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);

        return convertToCartResponseDTO(cart);
    }
}