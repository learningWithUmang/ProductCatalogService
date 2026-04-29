package dev.umang.productcatalogservice.controllers;

import dev.umang.productcatalogservice.dtos.SearchRequestDTO;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    /*
    Build a search API
    1. Request
    2. Response
    3. end point, http method
    4. delegate business logic to service


    /search -> POST

    Request body / Payload:-

    {
        "query": "laptop",
        "pageSize": 10,
        "pageNumber": 4
        "sortParams": [
            {
                "sortBy": "price",
                "sortDirection": "asc"
            },
            {
                "sortBy": "rating",
                "sortDirection": "desc"
                }
        ]
     */

    @Autowired
    private ISearchService searchService;

    @PostMapping("/search")
    public Page<Product> search(@RequestBody SearchRequestDTO searchRequestDTO) {
        // call the search service

        return searchService.search(
                searchRequestDTO.getQuery(),
                searchRequestDTO.getPageNumber(),
                searchRequestDTO.getPageSize(),
                searchRequestDTO.getSortParams()
        );
    }
}
