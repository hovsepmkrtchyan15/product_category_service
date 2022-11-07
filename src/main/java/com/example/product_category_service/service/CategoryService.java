package com.example.product_category_service.service;

import com.example.product_category_service.dto.CategoryResponseDto;
import com.example.product_category_service.dto.CreateCategoryDto;
import com.example.product_category_service.dto.UpdateCategoryDto;
import com.example.product_category_service.mapper.CategoryMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDto> findAll() {
        List<Category> all = categoryRepository.findAll();
        return categoryMapper.map(all);
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public void save(CreateCategoryDto createCategoryDto) {
        if (createCategoryDto == null) {
            throw new RuntimeException();
        }
        categoryRepository.save(categoryMapper.map(createCategoryDto));
    }

    public void update(UpdateCategoryDto updateCategoryDto) {
        Optional<Category> byId = categoryRepository.findById(updateCategoryDto.getId());
        if (byId.isEmpty()) {
            throw new NullPointerException();
        }
        categoryRepository.save(categoryMapper.map(updateCategoryDto));
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}
