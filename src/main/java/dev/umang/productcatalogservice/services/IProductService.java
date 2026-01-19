package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product createProduct(Product input);

    Product replaceProduct(Product input, Long productId);
}
