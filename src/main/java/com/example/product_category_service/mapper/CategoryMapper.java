package com.example.product_category_service.mapper;

import com.example.product_category_service.dto.CategoryResponseDto;
import com.example.product_category_service.dto.CreateCategoryDto;
import com.example.product_category_service.dto.UpdateCategoryDto;
import com.example.product_category_service.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category map(CreateCategoryDto categoryDto);

    CategoryResponseDto map(Category category);

    List<CategoryResponseDto> map(List<Category>categoryList);

    Category map(UpdateCategoryDto updateCategoryDto);


}
