package com.utkarsh.ecommerce.Service;

import com.utkarsh.ecommerce.DTO.ProductResponseDTO;
import com.utkarsh.ecommerce.Entity.Category;
import com.utkarsh.ecommerce.Entity.Product;
import com.utkarsh.ecommerce.dto.ProductRequestDTO;
import com.utkarsh.ecommerce.exception.ResourceNotFoundException;
import com.utkarsh.ecommerce.Repository.CategoryRepository;
import com.utkarsh.ecommerce.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {

        ProductResponseDTO responseDTO = new ProductResponseDTO();

        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStock(product.getStock());
        responseDTO.setCategoryId(product.getCategory().getId());
        responseDTO.setCategoryName(product.getCategory().getName());

        return responseDTO;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + requestDTO.getCategoryId()
                        ));

        Product product = new Product();

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return convertToResponseDTO(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (Product product : products) {
            responseList.add(convertToResponseDTO(product));
        }

        return responseList;
    }
    public Page<ProductResponseDTO> getProductsWithPagination(
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(this::convertToResponseDTO);
    }

    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        return convertToResponseDTO(product);
    }
    public ProductResponseDTO updateProduct(
            Long id, ProductRequestDTO requestDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + requestDTO.getCategoryId()
                        ));

        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return convertToResponseDTO(updatedProduct);
    }
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id
                        ));

        productRepository.delete(product);
    }
}