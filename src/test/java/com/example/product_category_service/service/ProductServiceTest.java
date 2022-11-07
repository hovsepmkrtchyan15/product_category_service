package com.example.product_category_service.service;

import com.example.product_category_service.dto.*;
import com.example.product_category_service.mapper.ProductMapper;
import com.example.product_category_service.model.Category;
import com.example.product_category_service.model.Product;
import com.example.product_category_service.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Product> products = Arrays.asList(
                new Product(1, "prod1", 2, 24,
                        Category.builder().id(1).name("cat1").build()),
                new Product(2, "prod2", 4, 240,
                        Category.builder().id(2).name("cat2").build()));


        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDto> all = productService.findAll();
        assertEquals(all, productMapper.map(products));

        verify(productRepository, times(1)).findAll();
    }


    @Test
    void findById() {
        int id = 1;
        Product product = Product.builder()
                .id(1)
                .title("prod1")
                .count(24)
                .price(656)
                .category(Category.builder().id(2).name("cat2").build())
                .build();
        Optional<Product> product1 = Optional.ofNullable(product);

        when(productRepository.findById(id)).thenReturn(product1);

        Optional<Product> byId = productService.findById(id);
        assertEquals(byId, product1);
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findByIdNull() {
        int id = 0;
        Optional<Product> product = Optional.of(new Product());

        when(productRepository.findById(id)).thenReturn(product);

        Optional<Product> byId = productService.findById(id);
        assertEquals(byId, product);

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void save() {
        CreateProductDto createProductDto = CreateProductDto.builder()
                .title("prod1")
                .count(24)
                .price(656)
                .category(Category.builder().id(2).name("cat2").build())
                .build();

        productService.save(createProductDto);

        verify(productRepository, times(1)).save(productMapper.map(createProductDto));
    }

    @Test
    void saveNull() {

        assertThrows(RuntimeException.class, () -> {
            productService.save(null);
        });
        verify(productRepository, times(0)).save(any());
    }

    @Test
    void update() {
        UpdateProductDto updateProductDto = UpdateProductDto.builder()
                .id(1)
                .title("prod1")
                .count(24)
                .price(656)
                .build();

        Product product = productMapper.map(updateProductDto);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.update(updateProductDto);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateNull() {

        UpdateProductDto updateProductDto = new UpdateProductDto();

        assertEquals(0, updateProductDto.getId());
        verify(productRepository, times(0)).save(any());

    }

    @Test
    void updateException() {

        UpdateProductDto updateProductDto = new UpdateProductDto();

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> {
            productService.update(updateProductDto);
        });

    }

    @Test
    void deleteById() {
        int id = 1;

        productService.deleteById(id);
        verify(productRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteByIdNull() {

        int id = 0;
        assertEquals(0, id);

        verify(productRepository, times(0)).deleteById(id);


    }

    @Test
    void findByCategoryId() {
        int categoryId = 1;
        List<Product> products = Arrays.asList(
                new Product(1, "prod1", 2, 24,
                        Category.builder().id(1).name("cat1").build()),
                new Product(2, "prod2", 4, 240,
                        Category.builder().id(2).name("cat2").build()));

        when(productRepository.findByCategoryId(categoryId)).thenReturn(products);

        List<ProductResponseDto> byCategoryId = productService.findByCategoryId(categoryId);
        assertEquals(byCategoryId, productMapper.map(products));
        verify(productRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void findByCategoryIdNull() {
        int categoryId = 0;
        List<Product> product = new ArrayList<>();

        when(productRepository.findByCategoryId(categoryId)).thenReturn(product);

        List<ProductResponseDto> byCategoryId = productService.findByCategoryId(categoryId);
        assertEquals(byCategoryId, productMapper.map(product));

        verify(productRepository, times(1)).findByCategoryId(categoryId);
    }
}