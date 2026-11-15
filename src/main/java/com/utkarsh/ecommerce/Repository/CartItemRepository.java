package com.utkarsh.ecommerce.Repository;

import com.utkarsh.ecommerce.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}