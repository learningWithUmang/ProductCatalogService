package dev.umang.productcatalogservice.services;

import dev.umang.productcatalogservice.dtos.SortParam;
import dev.umang.productcatalogservice.dtos.SortType;
import dev.umang.productcatalogservice.models.Product;
import dev.umang.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SearchService implements ISearchService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> searchProducts(String query,
                                        Integer pageNo,
                                        Integer pageSize,
                                        List<SortParam> sortParams) {

        /*
        List<SortParams>
        description, ASC

        sort = by description
        sort = by description, desc

        sort = by des desc and by price asc/desc and by name asc and by brand desc
         */

        Sort sort = null;

        if(!sortParams.isEmpty()){
            if(sortParams.get(0).getOrder().equals(SortType.ASC)){
                sort = sort.by(sortParams.get(0).getParamName()).ascending();
            }else{
                sort = sort.by(sortParams.get(0).getParamName()).descending();
            }

            for(int i = 1 ; i < sortParams.size() ; i++){
                if(sortParams.get(i).getOrder().equals(SortType.ASC)) {
                    sort = sort.and(Sort.by(sortParams.get(i).getParamName())).ascending();
                }else{
                    sort = sort.and(Sort.by(sortParams.get(i).getParamName())).descending();
                }
            }
        }

        return productRepository.findByName(
                query,
                PageRequest.of(pageNo, pageSize, sort));
    }
}
