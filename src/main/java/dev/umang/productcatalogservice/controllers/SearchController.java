package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.ProductDTO;
import dev.umang.productcatalogservice.dtos.SearchRequestDto;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    /*
    /search

    type: POST
     we have lot of things to pass in the request

     DTO

     */

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public Page<Product> search(@RequestBody SearchRequestDto searchRequestDto){
        Page<Product> products = searchService.searchProducts(
                searchRequestDto.getQuery(),
                searchRequestDto.getPageNo(),
                searchRequestDto.getPageSize(),
                searchRequestDto.getSortParams());



        return products;
    }

    /*
    Page
     */
}
