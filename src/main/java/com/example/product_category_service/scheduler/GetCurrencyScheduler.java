package com.example.product_category_service.scheduler;

import com.example.product_category_service.model.Product;
import com.example.product_category_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCurrencyScheduler {

    private final RestTemplate restTemplate;

    private final ProductRepository productRepository;
    @Value("${cb.url}")
    private String cbUrl;

    @Scheduled(cron = "0 * * * * *")
    public void getCurrencyFromCb() {
        DecimalFormat df = new DecimalFormat("#.##");
        List<Product> all = productRepository.findAll();
        if (!all.isEmpty()) {
            ResponseEntity<HashMap> currency = restTemplate.getForEntity(cbUrl + "?currency=USD", HashMap.class);
            HashMap<String, String> hashMap = currency.getBody();
            if (!hashMap.isEmpty()) {
                for (String s : hashMap.keySet()) {
                    System.out.println(s + "--" + hashMap.get(s));
                }
            }
        }
    }
}
