package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.ProductDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.IProductService;
import dev.umang.productcatalogservice.services.StorageProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockitoBean
    private StorageProductService productService;

    @Test
    @Transactional
    public void TestGetProductById_WithValidId_RunSuccessfully() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(11.00);

        //define the behaviour of the mock
        when(productService.getProductById(1L)).thenReturn(product);

        //Act
        ResponseEntity<ProductDTO> productDTOResponseEntity =
                productController.getProductById(1L);

        //Assert

        assertNotNull(productDTOResponseEntity);
        assertNotNull(productDTOResponseEntity.getBody());
        assertEquals(1L, productDTOResponseEntity.getBody().getId());
        assertEquals("Test Product", productDTOResponseEntity.getBody().getName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void TestGetProductById_WithNegativeId_ThrowsIllegalArgumentException(){

        //Arrange and Act
        assertThrows(IllegalArgumentException.class, () -> {
            productController.getProductById(-1L);
        });

        verify(productService, times(0)).getProductById(anyLong());
    }

    @Test
    public void testGetProductById_WithZeroId_ThrowsIllegalArgumentException(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.getProductById(0L);
        });
        assertEquals("Product id cannot be zero", exception.getMessage());
        verify(productService, times(0)).getProductById(anyLong());
    }

}

/*
@MockBean is a Spring Boot testing annotation used to add or replace
a bean in the Spring ApplicationContext with a Mockito mock during tests.

What @MockBean Does ?
It creates a Mockito mock of a bean.
It adds that mock to the Spring ApplicationContext.
If a real bean of the same type exists, it replaces it.
All other beans in the context will get this mock injected where needed.

The verify() method is used in Mockito to check whether a
mocked method was called, how many times, and with what arguments
during a test.


MockMvc tests are a way to test your Spring MVC controllers
without starting the full server.

They simulate HTTP requests (GET, POST, PUT…) and let you test:
1. Controller endpoints
2. Request validation
3. Request/response structure
4. HTTP status codes
5. JSON serialization/deserialization (Jackson)
6. Interactions with services (via mocks)


They are fast, lightweight, and focused.

 */