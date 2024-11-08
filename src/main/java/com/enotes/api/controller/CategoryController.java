package com.enotes.api.controller;


import com.enotes.api.dto.CategoryDto;
import com.enotes.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<String> saveCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("CategoryController.class and saveCategory() : ");
        CategoryDto categoryDto1 = categoryService.saveCategory(categoryDto);
        if(ObjectUtils.isEmpty(categoryDto1)){
            return new ResponseEntity<>("Object not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            return new ResponseEntity<>("Object Saved Successfully!", HttpStatus.CREATED);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        log.info("CategoryController.class and getAllCategory() : ");
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categoryDtoList)){
            return new ResponseEntity<>("Category list is not found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(categoryDtoList, HttpStatus.FOUND);
        }
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory(){
        log.info("CategoryController.class and getActiveCategory() : ");
        List<CategoryDto> categoryDtoList = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categoryDtoList)){
            return new ResponseEntity<>("Active Category list is not found", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(categoryDtoList, HttpStatus.FOUND);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCategoryDatails(@PathVariable String name){
        log.info("CategoryController.class and getCategoryDataiilsByName() with this name : "+ name);
        CategoryDto categoryDto = categoryService.getCategoryDataiilsByName(name);
        if(ObjectUtils.isEmpty(categoryDto)){
            return new ResponseEntity<>("Object not found", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategoryByName(@PathVariable String name){
        log.info("CategoryController.class and deleteCategoryByName() with this name : "+ name);
        String response = categoryService.deleteCategoryByName(name);
        if(response.isEmpty()){
            return new ResponseEntity<>("Object not found", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("CategoryController.class and updateCategory() : ");
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto);
        if(ObjectUtils.isEmpty(categoryDto1)){
            return new ResponseEntity<>("Object not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            return new ResponseEntity<>("Object updated Successfully!", HttpStatus.FOUND);
        }
    }
}
