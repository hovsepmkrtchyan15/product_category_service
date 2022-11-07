package com.example.product_category_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDto {

    private int id;
    private int userId;
    private String title;
    private boolean completed;
}
