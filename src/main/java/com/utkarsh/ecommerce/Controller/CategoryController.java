package com.utkarsh.ecommerce.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public String getCategories() {
        return "Get all categories";
    }

    @PostMapping
    public String createCategory() {
        return "Category created";
    }

    @PutMapping("/{id}")
    public String updateCategory() {
        return "Category updated";
    }

    @PatchMapping("/{id}")
    public String updateCategoryPartially() {
        return "Category partially updated";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory() {
        return "Category deleted";
    }
}