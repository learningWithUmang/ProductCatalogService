package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.dtos.SortParam;
import dev.umang.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {

    Page<Product> search(String query,
                        Integer pageNumber,
                        Integer pageSize,
                        List<SortParam> sortParams);
}
