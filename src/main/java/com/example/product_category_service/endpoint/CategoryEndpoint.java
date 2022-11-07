package com.example.product_category_service.endpoint;

import com.example.product_category_service.dto.CategoryResponseDto;
import com.example.product_category_service.dto.CreateCategoryDto;
import com.example.product_category_service.dto.UpdateCategoryDto;
import com.example.product_category_service.mapper.CategoryMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.security.CurrentUser;
import com.example.product_category_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Slf4j
public class CategoryEndpoint {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

//    Logger log = LoggerFactory.getLogger(CategoryEndpoint.class.getName());

    @Operation(operationId = "getAllCategory",
            summary = "Get all category",
            description = "description")
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory(
            @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("endpoint /categories called by {}", currentUser.getUser().getEmail());

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
