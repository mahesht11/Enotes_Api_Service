package com.enotes.api.serviceImpl;

import com.enotes.api.dto.CategoryDto;
import com.enotes.api.entity.Category;
import com.enotes.api.exception.ResourceNotFoundException;
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

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl.class and saveCategory() : ");

        Category category = modelMapper.map(categoryDto, Category.class);
        category.setIsActive(true);
        category.setIsDeleted(false);
        category.setCreatedOn(new Date());
        Category category1 = categoryRepository.saveAndFlush(category);
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        log.info("CategoryServiceImpl.class and getAllCategory() : ");
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public List<CategoryDto> getActiveCategory() {
        log.info("CategoryServiceImpl.class and getActiveCategory() : ");
        List<Category> categoryList = categoryRepository.findByIsActiveTrue();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryDataiilsById(Integer id) {
        log.info("CategoryServiceImpl.class and getCategoryDataiilsById() with this id : "+id);
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return modelMapper.map(category.get(), CategoryDto.class);
        }else{
            throw new ResourceNotFoundException("Resource not found with this Id : " + id);
        }

    }

    @Override
    public String deleteCategoryById(Integer id) {
        log.info("CategoryServiceImpl.class and deleteCategoryById() with this id : "+id);
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.delete(category.get());
            return "Object deleted the Successfully!";
        }else{
            throw new ResourceNotFoundException("Resource not found with this Id : " + id);
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("CategoryServiceImpl.class and updateCategory() : ");
        Optional<Category> category = categoryRepository.findByNameAndDescription(categoryDto.getName(), categoryDto.getDescription());
        if(category.isPresent()){
            Category category1 = modelMapper.map(categoryDto, Category.class);
            Category category2 = categoryRepository.saveAndFlush(category1);
            return modelMapper.map(category1, CategoryDto.class);
        }else{
            throw new ResourceNotFoundException("Resource not found with this name and description : " +categoryDto.getName()+ " , " +categoryDto.getDescription());
        }
    }
}
