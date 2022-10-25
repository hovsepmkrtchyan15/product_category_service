package com.example.product_category_service.dtoProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDto {

    private int id;
    private String title;
    private int count;
    private double price;
}
