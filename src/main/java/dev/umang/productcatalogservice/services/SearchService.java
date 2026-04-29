package dev.umang.productcatalogservice.services;


import dev.umang.productcatalogservice.dtos.SortDirection;
import dev.umang.productcatalogservice.dtos.SortParam;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Page<Product> search(String query,
                                Integer pageNumber,
                                Integer pageSize,
                                List<SortParam> sortParams) {
        // Implement the search logic here
        /*
        What to do with sortParams?
         */

        Sort sort = null;


        if(sortParams != null && !sortParams.isEmpty()){

            /*
            like builder design patter
            sort.sortyBt().and(sortBy(and......
             */

            if(sortParams.get(0).getSortDirection().equals(SortDirection.ASC)) {
                sort = sort.by(sortParams.get(0).getSortBy()).ascending();
            }else {
                sort = sort.by(sortParams.get(0).getSortBy()).descending();
            }

            for(int i = 1 ; i < sortParams.size() ; i++){
                SortParam sortParam = sortParams.get(i);
                if(sortParam.getSortDirection().equals(SortDirection.ASC)) {
                    sort = sort.and(Sort.by(sortParam.getSortBy()));
                }else {
                    sort = sort.and(Sort.by(sortParam.getSortBy()).descending());
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return productRepository.findByName(query, pageable);
    }
}
