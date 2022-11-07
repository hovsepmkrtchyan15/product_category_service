package com.example.product_category_service.service;

import com.example.product_category_service.dto.CategoryResponseDto;
import com.example.product_category_service.dto.CreateCategoryDto;
import com.example.product_category_service.dto.UpdateCategoryDto;
import com.example.product_category_service.mapper.CategoryMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Category> categories = Arrays.asList(
                new Category(1, "category1"),
                new Category(2, "category2"));


        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponseDto> all = categoryService.findAll();
        assertEquals(all, categoryMapper.map(categories));

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        int id = 1;
        Category category = Category.builder()
                .id(1)
                .name("category1")
                .build();
        Optional<Category> category1 = Optional.ofNullable(category);

        when(categoryRepository.findById(id)).thenReturn(category1);

        Optional<Category> byId = categoryService.findById(id);
        assertEquals(byId, category1);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNull() {
        int id = 0;
        Optional<Category> category = Optional.of(new Category());

        when(categoryRepository.findById(id)).thenReturn(category);

        Optional<Category> byId = categoryService.findById(id);
        assertEquals(byId, category);

        verify(categoryRepository, times(1)).findById(id);

    }

    @Test
    void save() {
        CreateCategoryDto categoryDto = CreateCategoryDto.builder()
                .name("category")
                .build();

        categoryService.save(categoryDto);

        verify(categoryRepository, times(1)).save(categoryMapper.map(categoryDto));
    }

    @Test
    void saveNull() {

        assertThrows(RuntimeException.class, () -> {
            categoryService.save(null);
        });
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void update() {

        UpdateCategoryDto updateCategoryDto = UpdateCategoryDto.builder()
                .id(2)
                .name("category1")
                .build();

        Category category = categoryMapper.map(updateCategoryDto);

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        categoryService.update(updateCategoryDto);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateNull() {

        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto();

        assertEquals(0, updateCategoryDto.getId());
        verify(categoryRepository, times(0)).save(any());


    }

    @Test
    void updateException() {

        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto();

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> {
            categoryService.update(updateCategoryDto);
        });

    }

    @Test
    void deleteById() {

        int id = 1;

        categoryService.deleteById(id);
        verify(categoryRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteByIdNull() {

        int id = 0;
        assertEquals(0, id);

        verify(categoryRepository, times(0)).deleteById(id);

    }
}