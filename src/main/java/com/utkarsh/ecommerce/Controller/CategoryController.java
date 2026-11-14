package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.DTO.CategoryRequestDTO;
import com.utkarsh.ecommerce.DTO.CategoryResponseDTO;
import com.utkarsh.ecommerce.Entity.Category;
import com.utkarsh.ecommerce.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor Injection
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO requestDTO) {

        return categoryService.createCategory(requestDTO);
    }

    @GetMapping
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO requestDTO) {

        return categoryService.updateCategory(id, requestDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}