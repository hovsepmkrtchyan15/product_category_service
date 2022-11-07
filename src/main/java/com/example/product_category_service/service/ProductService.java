package com.example.product_category_service.service;

import com.example.product_category_service.dto.CreateProductDto;
import com.example.product_category_service.dto.ProductResponseDto;
import com.example.product_category_service.dto.UpdateProductDto;
import com.example.product_category_service.mapper.ProductMapper;
import com.example.product_category_service.model.Product;
import com.example.product_category_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;
    @Value("${cb.url}")
    private String cbUrl;

    public List<ProductResponseDto> findAll() {
        DecimalFormat df = new DecimalFormat("#.##");
        List<Product> all = productRepository.findAll();
        if (!all.isEmpty()) {
            ResponseEntity<HashMap> currency = restTemplate.getForEntity(cbUrl + "?currency=USD", HashMap.class);
            HashMap<String, String> hashMap = currency.getBody();
            if (!hashMap.isEmpty()) {
                double usdCurrency = Double.parseDouble(hashMap.get("USD"));
                if (usdCurrency > 0) {
                    for (Product product : all) {
                        double price = product.getPrice() / usdCurrency;
                        product.setPrice(price);
                    }
                }
            }
        }

        return productMapper.map(all);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public void save(CreateProductDto createProductDto) {
        if (createProductDto == null) {
            throw new RuntimeException();
        }
        productRepository.save(productMapper.map(createProductDto));
    }

    public void update(UpdateProductDto updateProductDto) {
        Optional<Product> byId = productRepository.findById(updateProductDto.getId());
        if(byId.isEmpty()){
            throw new NullPointerException();
        }
        productRepository.save(productMapper.map(updateProductDto));
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponseDto> findByCategoryId(int id) {
        return productMapper.map(productRepository.findByCategoryId(id));
    }
}
