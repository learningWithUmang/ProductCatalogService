package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.dtos.UserDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.models.State;
import dev.umang.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("storageProductService")
@Primary
public class StorageProductService implements IProductService{

    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    public StorageProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product input) {
        Optional<Product> optionalProduct = productRepository.findById(input.getId());

        if(optionalProduct.isEmpty()){
            return productRepository.save(input);
        }else{
            // we can throw an exception that product already exists
            return null;
        }
    }

    //PUT request
    //this will update the entire product
    @Override
    public Product replaceProduct(Product input, Long productId) {
        /*
        productId = 10
        input
        id = 10
        name = "iPhone 15"
        category = "electronics"
        createdAt = <should be same as existing>

        input

         */
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty()){
            //Don't create a new product since we are just replacing
            return null;
        }else{
            input.setId(productId);
            input.setCreatedAt(optionalProduct.get().getCreatedAt());

            return productRepository.save(input);
        }
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long userId) {

        Optional<Product> product = productRepository.findById(productId);

        /*
        check if product is listed or not
         */

        //product is not listed
        //call into the user service

        try {
            UserDTO userDto = restTemplate.getForEntity(
                    "http://userauthservice/users/{userId}",
                    UserDTO.class,
                    userId).getBody();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        /*
        get list of ips of userauthservice from eureka server
        using client side load balancing to call the userauthservice
         */


        return null;
    }


    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()) {
            return false;
        }else{
            Product product = optionalProduct.get();
            if(product.getState().equals(State.ACTIVE)){
                product.setState(State.INACTIVE);
                productRepository.save(product);
                return true;
            }
            return false;
        }
    }

    /*
    HW: Implement updateProduct
    Wherever you're fetching products, you should make sure they are active
     */
}
/*
JPA Auditing
 */