package com.example.product_category_service.dto;

import com.example.product_category_service.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private int id;
    private String title;
    private int count;
    private double price;
    private Category category;


}
