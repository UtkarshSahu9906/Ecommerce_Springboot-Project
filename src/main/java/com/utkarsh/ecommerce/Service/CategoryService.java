package com.utkarsh.ecommerce.Service;


import com.utkarsh.ecommerce.DTO.CategoryRequestDTO;
import com.utkarsh.ecommerce.DTO.CategoryResponseDTO;
import com.utkarsh.ecommerce.Entity.Category;
import com.utkarsh.ecommerce.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    }public List<CategoryResponseDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponseDTO> responseList = new ArrayList<>();

        for (Category category : categories) {
            responseList.add(convertToResponseDTO(category));
        }

        return responseList;
    }
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));

        return convertToResponseDTO(category);
    }
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));

        existingCategory.setName(requestDTO.getName());
        existingCategory.setDescription(requestDTO.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);

        return convertToResponseDTO(updatedCategory);
    }
    private CategoryResponseDTO convertToResponseDTO(Category category) {

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();

        responseDTO.setId(category.getId());
        responseDTO.setName(category.getName());
        responseDTO.setDescription(category.getDescription());

        return responseDTO;
    }
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found with id: " + id));

        categoryRepository.delete(category);
    }
}