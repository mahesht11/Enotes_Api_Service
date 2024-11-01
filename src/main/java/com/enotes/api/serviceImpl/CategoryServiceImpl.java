package com.enotes.api.serviceImpl;

import com.enotes.api.repository.CategoryRepository;
import com.enotes.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
}
