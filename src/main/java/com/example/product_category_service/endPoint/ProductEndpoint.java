package com.example.product_category_service.endPoint;

import com.example.product_category_service.dtoProduct.CreateProductDto;
import com.example.product_category_service.dtoProduct.ProductResponseDto;
import com.example.product_category_service.dtoProduct.UpdateProductDto;
import com.example.product_category_service.mapper.ProductMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.model.Product;
import com.example.product_category_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
      return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponseDto>> getByCategoryId(@RequestBody Category category) {
        List<ProductResponseDto> byCategoryId = productService.findByCategoryId(category.getId());
        if (byCategoryId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byCategoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") int id) {
        Optional<Product> byId = productService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.map(byId.get()));
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto createProductDto) {
        productService.save(createProductDto);
        return ResponseEntity.ok(createProductDto);
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDto updateProductDto) {
        if (updateProductDto.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        productService.update(updateProductDto);
        return ResponseEntity.ok(updateProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
