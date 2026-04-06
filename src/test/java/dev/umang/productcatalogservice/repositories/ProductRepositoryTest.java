package dev.umang.productcatalogservice.repositories;

import dev.umang.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testJPAMethods(){
        List<Product> products = productRepository.findAllByOrderByPrice();
        System.out.println();
    }
}