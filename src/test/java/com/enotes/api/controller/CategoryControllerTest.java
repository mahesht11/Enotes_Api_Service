package com.enotes.api.controller;

import static org.mockito.Mockito.when;

import com.enotes.api.dto.CategoryDto;
import com.enotes.api.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("Test saveCategory(CategoryDto)")
    void testSaveCategory() throws Exception {

        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/api/v1/category/save-category", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCreatedBy(1);
        categoryDto.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        categoryDto.setDescription("The characteristics of someone or something");
        categoryDto.setIsActive(true);
        categoryDto.setName("Name");
        categoryDto.setUpdatedBy(1);
        categoryDto.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(categoryDto));
        Object[] controllers = new Object[]{categoryController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();
    }

    @Test
    @DisplayName("Test getAllCategory(); given ArrayList() add CategoryDto(); then status isFound()")
    void testGetAllCategory_givenArrayListAddCategoryDto_thenStatusIsFound() throws Exception {
        // Arrange
        ArrayList<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(new CategoryDto());
        when(categoryService.getAllCategory()).thenReturn(categoryDtoList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"name\":null,\"description\":null,\"isActive\":null,\"createdBy\":null,\"createdOn\":null,\"updatedBy\":null,"
                                        + "\"updatedOn\":null}]"));
    }

    @Test
    @DisplayName("Test getAllCategory(); then status isNotFound()")
    void testGetAllCategory_thenStatusIsNotFound() throws Exception {
        // Arrange
        when(categoryService.getAllCategory()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/");
        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder);
        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Category list is not found"));
    }


    @Test
    @DisplayName("Test getActiveCategory(); given ArrayList() add CategoryDto(); then status isFound()")
    void testGetActiveCategory_givenArrayListAddCategoryDto_thenStatusIsFound() throws Exception {
        // Arrange
        ArrayList<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(new CategoryDto());
        when(categoryService.getActiveCategory()).thenReturn(categoryDtoList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/active-category");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"name\":null,\"description\":null,\"isActive\":null,\"createdBy\":null,\"createdOn\":null,\"updatedBy\":null,"
                                        + "\"updatedOn\":null}]"));
    }


    @Test
    @DisplayName("Test getActiveCategory(); then status isNotFound()")
    void testGetActiveCategory_thenStatusIsNotFound() throws Exception {
        // Arrange
        when(categoryService.getActiveCategory()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/active-category");
        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder);
        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Active Category list is not found"));
    }


    @Test
    @DisplayName("Test getCategoryDatails(String); given ArrayList() add CategoryDto(); then status isFound()")
    void testGetCategoryDatails_givenArrayListAddCategoryDto_thenStatusIsFound() throws Exception {
        // Arrange
        ArrayList<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(new CategoryDto());
        when(categoryService.getAllCategory()).thenReturn(categoryDtoList);
        when(categoryService.getCategoryDataiilsByName(Mockito.<String>any())).thenReturn(new CategoryDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/{name}", "",
                "Uri Variables");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"name\":null,\"description\":null,\"isActive\":null,\"createdBy\":null,\"createdOn\":null,\"updatedBy\":null,"
                                        + "\"updatedOn\":null}]"));
    }

    @Test
    @DisplayName("Test getCategoryDatails(String); then content string 'Category list is not found'")
    void testGetCategoryDatails_thenContentStringCategoryListIsNotFound() throws Exception {
        // Arrange
        when(categoryService.getAllCategory()).thenReturn(new ArrayList<>());
        when(categoryService.getCategoryDataiilsByName(Mockito.<String>any())).thenReturn(new CategoryDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/{name}", "",
                "Uri Variables");
        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder);
        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Category list is not found"));
    }
/*
    @Test
    @DisplayName("Test getCategoryDatails(String); then content string 'Object not found'")
    void testGetCategoryDatails_thenContentStringObjectNotFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryDataiilsByName(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/{name}", "Name");
        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder);
        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Object not found"));
    }*/

    @Test
    @DisplayName("Test getCategoryDatails(String); when 'Name'; then status isFound()")
    void testGetCategoryDatails_whenName_thenStatusIsFound() throws Exception {
        // Arrange
        when(categoryService.getCategoryDataiilsByName(Mockito.<String>any())).thenReturn(new CategoryDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/category/{name}", "Name");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"name\":null,\"description\":null,\"isActive\":null,\"createdBy\":null,\"createdOn\":null,\"updatedBy\":null,"
                                        + "\"updatedOn\":null}"));
    }


    @Test
    @DisplayName("Test deleteCategoryByName(String); then status isFound()")
    void testDeleteCategoryByName_thenStatusIsFound() throws Exception {
        // Arrange
        when(categoryService.deleteCategoryByName(Mockito.<String>any())).thenReturn("Delete Category By Name");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/category/{name}", "Name");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Category By Name"));
    }

    @Test
    @DisplayName("Test deleteCategoryByName(String); then status isNotFound()")
    void testDeleteCategoryByName_thenStatusIsNotFound() throws Exception {
        // Arrange
        when(categoryService.deleteCategoryByName(Mockito.<String>any())).thenReturn("");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/category/{name}", "Name");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Object not found"));
    }

    @Test
    @DisplayName("Test updateCategory(CategoryDto)")
    //@Disabled("TODO: Complete this test")
    void testUpdateCategory() throws Exception {
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/api/v1/category/update", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCreatedBy(1);
        categoryDto.setCreatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        categoryDto.setDescription("The characteristics of someone or something");
        categoryDto.setIsActive(true);
        categoryDto.setName("Name");
        categoryDto.setUpdatedBy(1);
        categoryDto.setUpdatedOn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(categoryDto));
        Object[] controllers = new Object[]{categoryController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();
        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
    }
}
