package com.utkarsh.ecommerce.Controller;

import com.utkarsh.ecommerce.Entity.Category;
import com.utkarsh.ecommerce.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor Injection
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {

        return categoryService.createCategory(category);
    }
}