package com.example.product_category_service.endPoint;

import com.example.product_category_service.dtoCategory.CategoryResponseDto;
import com.example.product_category_service.dtoCategory.CreateCategoryDto;
import com.example.product_category_service.dtoCategory.UpdateCategoryDto;
import com.example.product_category_service.mapper.CategoryMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorys")
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getProductById(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.map(byId.get()));
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.save(createCategoryDto);
        return ResponseEntity.ok(createCategoryDto);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody UpdateCategoryDto updateCategoryDto) {
        if (updateCategoryDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.update(updateCategoryDto);
        return ResponseEntity.ok(updateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
