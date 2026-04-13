package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.ProductDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.StorageProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMVCTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StorageProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_RunSuccessfully_Ok() throws Exception {
        mockMvc.perform(
                get("/products")).
                andExpect(status().isOk());
    }

    @Test
    public void TestGetAllProducts_RunSuccessfully() throws Exception {
        //Arrange (mocking the service layer)

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Test Product 1");
        p1.setDescription("Test Description 1");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Test Product 2");
        p2.setDescription("Test Description 2");

        List<Product> products = List.of(p1, p2);

        when(productService.getAllProducts()).thenReturn(products);

        //define the expected output
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product: products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTOS.add(productDTO);
        }

        String expectedResponse = objectMapper.writeValueAsString(productDTOS);
        System.out.println(expectedResponse);

        mockMvc.perform(
                get("/products")).
                andExpect(status().isOk()).
                andExpect(content().json(expectedResponse)).
                andExpect(jsonPath("$[0].name").value("Test Product 1"));

    }

    @Test
    public void testCreateProduct_RunSuccessfully() throws Exception {
        ProductDTO productDto = new ProductDTO();
        productDto.setId(10L);
        productDto.setName("Ipad");
        productDto.setPrice(75000D);

        Product product = new Product();
        product.setId(10L);
        product.setName("Ipad");
        product.setPrice(75000D);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ProductDTO responseDTO = product.convert();
        String response = objectMapper.writeValueAsString(responseDTO);


        String requestBody = objectMapper.writeValueAsString(productDto);



        mockMvc.perform(post("/products")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))       // Important
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andExpect(jsonPath("$.name").value("Ipad"));



    }

    /*
    /products GET
    Response Body
    {
    "products": [
        {
            "id": 1,
            "name": "Test Product 1",
            "description": "Test Description 1"
        },
        {
            "id": 2,
            "name": "Test Product 2",
            "description": "Test Description 2"
        }
    }

    mocked the service layer - @mockitobean
    mocked the service output
     */
}
