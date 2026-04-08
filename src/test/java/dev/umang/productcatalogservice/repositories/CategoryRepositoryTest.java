package dev.umang.productcatalogservice.repositories;

import dev.umang.productcatalogservice.models.Category;
import dev.umang.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;


//    @Test
//    @Transactional
//    public void testFetchTypes(){
//        Optional<Category> optionalCategory = categoryRepository.findById(1L);
//
//        if(optionalCategory.isPresent()){
//            Category category = optionalCategory.get();
//            //System.out.println(category.getProducts().size());
//        }
//    }

    @Test
    @Transactional
    public void NPlusOne(){
        List<Category> categories = categoryRepository.findAll();
        for(Category category: categories){
            for(Product product : category.getProducts()){
                System.out.println(product.getName());
            }
        }
        /*
        We will see 1 query to get categories and
        N queries for each category accessing Product Table for it's own products
        This is known as N+1 Problem

        1. Use FetchMode.SUB_SELECT

         */
    }

}

/*
Scenarios

LAZY/Default
1. ask for only category data -> 1
2. ask for both category and products data -> 2

EAGER(regardless you ask or not, it'll get all child entities which are eager fetch type)
1. ask for category only -> 1
2. ask for category and products data -> 1

LAZY and SELECT  -> default combination
LAZY and JOIN -> making lazy eager

EAGER and SELECT -> additional queries
EAGER and JOIN -> default combination

LAZY AND SUB_SELECT
EAGER AND SUB_SELECT



N = 1M
1M categories
good idea to run a separate query altogether for 1 category?


EAGER - you're bringing everything at once?
eager + join
1 query overall, with all data(each category with it's products)
1M categories, each category having 10k products on average

query to db, which returns the data which in turn gets stored in your RAM

10^6 * 10^4 * 1000B = 10^13 B = 10 TB (Not even possible)



LAZY + SELECT -> N + 1 queries (no of queries) each category fires a new query
EAGER -> this might bring lot of data, memory overflow can happen, break
LAZY + SUBSELECT -> 2 queries, but second query is bringing almost everything
Batching (lazy + select) -> in one go, you'll bring products for 'n' categories


1 to bring all catagories data
N / n to bring products data for all categories

1 + N/n queries, where n is the batch size

1 + 4/2 = 3 queries

 */