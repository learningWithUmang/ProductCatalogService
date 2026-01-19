package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.dtos.FakestoreProductDto;
import dev.umang.productcatalogservice.models.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Fallback;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/*
Spring cloud class - more on configurations
Rest template is a library we are going to use for talking to 3rd party APIs
 */
@Service
public class FakestoreProductService implements IProductService {
    private RestTemplate restTemplate;

    private FakestoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public Product getProductById(Long id) {
//        FakestoreProductDto fakestoreProductDto = restTemplate.getForObject(
//                "https://fakestoreapi.com/products/{id}",
//                FakestoreProductDto.class,
//                id);

        ResponseEntity<FakestoreProductDto> fakestoreProductDtoResponseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                FakestoreProductDto.class,
                id
        );



        if(fakestoreProductDtoResponseEntity.hasBody() &&
                fakestoreProductDtoResponseEntity.getStatusCode().equals(
                        HttpStatusCode.valueOf(200)
        )){
            return fakestoreProductDtoResponseEntity.getBody().from(fakestoreProductDtoResponseEntity.getBody());
        }

        return null;
        /*
        FakestoreProductDTO to Product
        A to B

        a.convert(a) -> b

        B{
            convert(){}
        }
         */


    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        ResponseEntity<FakestoreProductDto[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakestoreProductDto[].class);

        if(response.hasBody() &&
                response.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            FakestoreProductDto[] fakestoreProductDtos = response.getBody();

            for(FakestoreProductDto fakestoreProductDto : fakestoreProductDtos){
                products.add(fakestoreProductDto.from(fakestoreProductDto));
            }

            return products;

        }

        return null;
        /*
        List class
        common functionalities
        add, remove, update, size, find, etc
        List<T> => List<T>
         */



    }

    @Override
    public Product createProduct(Product input) {
        return null;
    }

    /*
    Generics?
    <T>
    Type erasure
    1. Compiling(Writing code)
    2. Runtime(Running code)

    List<String>
    List<Integer>
    Java checks your code to make sure you are not putting
    an integer into a string list. very strict.

    However, as soon as you compile the code, java "erases" the specific
    type inside the brackets

    List<T> ls = new List<>();


    List => class

    List<Integer> just becomes a list for Java
    List<String> just becomes a list for Java

    Rest templare needs to know exactly what class to create.
    Solution - Use arrays as they don't suffer from type erasure.

    Why do we have type erasure? - concept of generics.



    PUT - replaces/updates the entire product
    PATCH - partially updates the product
     */

    public Product replaceProduct(Product product, Long id){
        /*
        Billion dollar question?
         */
        FakestoreProductDto fakestoreProductDto = product.convertToFakeStoreProduct();

        ResponseEntity<FakestoreProductDto> response = this.putForEntity(
                "https://fakestoreapi.com/products/{id}",
                fakestoreProductDto,
                FakestoreProductDto.class,
                id
        );

        if(response.hasBody() &&
                response.getStatusCode().equals(HttpStatusCode.valueOf(200))){
//            FakestoreProductDto fakestoreProductDto1 = response.getBody();
//            return fakestoreProductDto1.from(fakestoreProductDto1);
            return product;
        }

        return  null;
    }
}
