package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.models.State;
import dev.umang.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("storageProductService")
@Primary
public class StorageProductService implements IProductService {

    private ProductRepository productRepository;

    public StorageProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product input) {
        Optional<Product> productOptional = productRepository.findById(input.getId());
        if(productOptional.isEmpty()){
            return productRepository.save(input);
        }else{
            return null;
        }
    }

    @Override
    public Product replaceProduct(Product input, Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isPresent()){
            input.setId(productId);
            input.setLastUpdatedAt(new Date());
            return productRepository.save(input);
        }else{
            return null;
        }
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            if(product.getState().equals(State.ACTIVE)){
                product.setState(State.INACTIVE);
                product.setLastUpdatedAt(new Date());
                productRepository.save(product);
            }else{
                productRepository.deleteById(productId);
            }
            return true;
        }else {
            return false;
        }
    }
}
/*
GET
POST
DELETE
PUT vs PATCH
PUT -> entirely replace the resource
PATCH -> partially update the resource

earlier, the product with id 1 was:-
product = {
    "id": 1,
    "price": 999.99,
    "name": "iPhone 14 Pro",
    "description": "The iPhone 14 Pro is the recent flagship smartphone from Apple, released in September 2022. It features a stunning 6.1-inch Super Retina XDR display, powered by the A16 Bionic chip for lightning-fast performance. The device boasts a triple-camera system with advanced computational photography capabilities, allowing users to capture stunning photos and videos. With its sleek design, improved battery life, and enhanced security features, the iPhone 14 Pro offers an exceptional user experience for tech enthusiasts and Apple fans alike."
}

productId = 1,
input  = {
    "price": 1099.99,
    "name": "iPhone 14 Pro Max",
    "description": "The iPhone 14 Pro Max is the latest flagship smartphone from Apple, released in September 2022. It features a stunning 6.7-inch Super Retina XDR display, powered by the A16 Bionic chip for lightning-fast performance. The device boasts a triple-camera system with advanced computational photography capabilities, allowing users to capture stunning photos and videos. With its sleek design, improved battery life, and enhanced security features, the iPhone 14 Pro Max offers an exceptional user experience for tech enthusiasts and Apple fans alike."

    Generally, we don't get Ids in objects from the client
 */


/*
HW -> Try to also implement PATCH logic for updateProduct


whatever classes you annotate, spring creates their objects at the application
boot time and puts it in the spring app context.
 */