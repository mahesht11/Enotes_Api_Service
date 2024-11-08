package com.enotes.api.service;

import com.enotes.api.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDto saveCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();

    List<CategoryDto> getActiveCategory();

    CategoryDto getCategoryDataiilsByName(String name);

    String deleteCategoryByName(String name);

    CategoryDto updateCategory(CategoryDto categoryDto);
}
