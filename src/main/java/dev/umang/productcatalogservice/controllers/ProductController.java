package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.ProductDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.FakestoreProductService;
import dev.umang.productcatalogservice.services.IProductService;
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
    ProductDTO createProduct(@RequestBody ProductDTO product){

        ProductDTO productReponseDTO = new ProductDTO();
        /*
        call the service layer to save the product
         */

        //productService.createProduct(product);
        return productReponseDTO;
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

        if(id < 1){
            throw new IllegalArgumentException("Invalid Product ID(zero or negative)");
        }



        Product product = productService.getProductById(id);


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
