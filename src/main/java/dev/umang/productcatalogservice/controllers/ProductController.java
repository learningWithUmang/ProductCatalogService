package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.ProductDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.FakestoreProductService;
import dev.umang.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/*
multiple objects of controller
req1
req2
 */
@RestController
public class ProductController {
    IProductService productService;

    /*
    Constructor injection
     */

    public ProductController(IProductService productService){
        this.productService = productService;
    }

    /*
    1. Create product
    2. Get product by id
    3. Get all products
     */


    /*
    Create product ("/products"), POST
    Get product by id ("/products/{id}"), GET
    Get all products ("/products"), GET
    @RequestMapping("/products")
    @GetMapping
    @PostMapping
     */

    /*
    /products/24 -> product with id 24
    /products/. -> productID is null
     */
    @PutMapping("/products/{productId}")
    ProductDTO updateProduct(@PathVariable("productId") Long productId,
                         @RequestBody ProductDTO productDTO){

        /*
        productDto to product
        pass productDTO and get the product back
         */
        ProductDTO productReponseDTO = new ProductDTO();
        /*
        call the service layer to update the product
         */

        Product product = productService.replaceProduct(productDTO.convertToProduct(), productId);

        if(product != null){
            return product.convert();
        }
        return null;
    }


    @PostMapping("/products")
    ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        Product product = productDTO.convertToProduct();

        Product outPut = productService.createProduct(product);

        return outPut.convert();
    }
    /*
    "name": "iphone",
    "descr": "apple",

     */
    @GetMapping("/products/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id){
        //RestTemplate
        /*
        call the service layer to get the product by id
         */

        if(id < 0){
            throw new IllegalArgumentException("Product id cannot be negative");
        }else if(id == 0){
            throw new IllegalArgumentException("Product id cannot be zero");
        }



        Product product = productService.getProductById(id);
        //product we have hardcoded will be returned it


        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



        /*
        product
        to productDTO

        obj.from(obj) -> newObj
         */

        ProductDTO productDTO = product.convert();

        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @GetMapping("/products")
    List<ProductDTO> getAllProducts(){
        List<ProductDTO> productDTOS = new ArrayList<>();
        /*
        call the service layer to get all products
         */

        List<Product> products = productService.getAllProducts();

        if(products != null){
            for(Product product : products){
                productDTOS.add(product.convert());
            }
        }

        return productDTOS;
    }



}
/*
path variable /id/
request body { "" : ""
Query params ?category=electronics
 */
