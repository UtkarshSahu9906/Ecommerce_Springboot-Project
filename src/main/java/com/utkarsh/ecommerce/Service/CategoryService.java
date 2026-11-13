package com.utkarsh.ecommerce.Service;


import com.utkarsh.ecommerce.DTO.CategoryRequestDTO;
import com.utkarsh.ecommerce.DTO.CategoryResponseDTO;
import com.utkarsh.ecommerce.Entity.Category;
import com.utkarsh.ecommerce.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

        // Convert DTO into Entity
        Category category = new Category();

        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        // Save Entity into database
        Category savedCategory = categoryRepository.save(category);

        // Convert Entity into Response DTO
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();

        responseDTO.setId(savedCategory.getId());
        responseDTO.setName(savedCategory.getName());
        responseDTO.setDescription(savedCategory.getDescription());

        return responseDTO;
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }
    public Category updateCategory(Long id, Category updatedCategory) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existingCategory);
    }
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));

        categoryRepository.delete(category);
    }
}