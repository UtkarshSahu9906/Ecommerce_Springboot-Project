package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.DTO.ProductResponseDTO;
import com.utkarsh.ecommerce.Service.ProductService;
import com.utkarsh.ecommerce.dto.ProductRequestDTO;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(
            @RequestParam String name) {

        return productService.searchProductsByName(name);
    }

    @PostMapping
    public ProductResponseDTO createProduct(
            @Valid @RequestBody ProductRequestDTO requestDTO) {

        return productService.createProduct(requestDTO);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO requestDTO) {

        return productService.updateProduct(id, requestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/page")
    public Page<ProductResponseDTO> getProductsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return productService.getProductsWithPagination(page, size);
    }
}