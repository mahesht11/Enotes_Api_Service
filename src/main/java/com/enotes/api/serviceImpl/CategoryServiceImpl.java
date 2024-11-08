package com.enotes.api.serviceImpl;

import com.enotes.api.dto.CategoryDto;
import com.enotes.api.entity.Category;
import com.enotes.api.exception.ResourceNotFoundException;
import com.enotes.api.exception.ResourseExistException;
import com.enotes.api.repository.CategoryRepository;
import com.enotes.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String NOT_FOUND = "Resource not found with this name : ";

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl.class and saveCategory() : ");
        Category category = modelMapper.map(categoryDto, Category.class);
        Optional<Category> category1 = categoryRepository.findByName(category.getName());
        if(category1.isPresent()) {
            throw new ResourseExistException("Category is already exist");
        }
        category.setIsActive(true);
        category.setIsDeleted(false);
        category.setCreatedOn(new Date());
        Category category2 = categoryRepository.saveAndFlush(category);
        return modelMapper.map(category2, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        log.info("CategoryServiceImpl.class and getAllCategory() : ");
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getActiveCategory() {
        log.info("CategoryServiceImpl.class and getActiveCategory() : ");
        List<Category> categoryList = categoryRepository.findByIsActiveTrue();
        return categoryList.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryDataiilsByName(String name) {
        log.info("CategoryServiceImpl.class and getCategoryDataiilsByName() with this name : "+ name);
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()){
            return modelMapper.map(category.get(), CategoryDto.class);
        }else{
            throw new ResourceNotFoundException(NOT_FOUND + name);
        }

    }

    @Override
    public String deleteCategoryByName(String name) {
        log.info("CategoryServiceImpl.class and deleteCategoryById() with this name : "+ name);
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()){
            categoryRepository.delete(category.get());
            return "Object deleted the Successfully!";
        }else{
            throw new ResourceNotFoundException(NOT_FOUND + name);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl.class and updateCategory() : ");
        Optional<Category> category = categoryRepository.findByName(categoryDto.getName());
        if(category.isPresent()){
            Category category1 = modelMapper.map(categoryDto, Category.class);
            category.get().setUpdatedBy(1);
            category.get().setDescription(category1.getDescription());
            category.get().setUpdatedOn(new Date());
            Category category2 = categoryRepository.saveAndFlush(category.get());
            return modelMapper.map(category2, CategoryDto.class);
        }else{
            throw new ResourceNotFoundException(NOT_FOUND +categoryDto.getName());
        }
    }
}
