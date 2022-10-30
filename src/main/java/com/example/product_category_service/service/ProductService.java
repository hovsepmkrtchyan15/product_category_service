package com.example.product_category_service.service;

import com.example.product_category_service.dto.CreateProductDto;
import com.example.product_category_service.dto.ProductResponseDto;
import com.example.product_category_service.dto.UpdateProductDto;
import com.example.product_category_service.mapper.ProductMapper;
import com.example.product_category_service.model.Product;
import com.example.product_category_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponseDto> findAll(){
        return productMapper.map(productRepository.findAll());
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void save(CreateProductDto createProductDto) {
        productRepository.save(productMapper.map(createProductDto));
    }
    public void update(UpdateProductDto updateProductDto){
        productRepository.save(productMapper.map(updateProductDto));
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponseDto> findByCategoryId(int id) {
        return productMapper.map(productRepository.findByCategoryId(id));
    }
}
