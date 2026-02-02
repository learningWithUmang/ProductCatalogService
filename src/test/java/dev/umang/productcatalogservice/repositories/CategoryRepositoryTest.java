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

    @Test
    @Transactional
    public void NPlusOne(){
        /*
        Scenario where you want to fetch all categories along with corresponding
        products
         */

        List<Category> categories = categoryRepository.findAll();
        /*
        Assume there are N categories, tell me how many queries will be executed

        select * from categories;   => 1
        for each category => select * from products where category_id = ?;  => N

        N + 1 queries
         */

        for(Category category: categories){
            // for each category i want to get all products

            for(Product product: category.getProducts()){
                System.out.println(product.getName());
            }
        }

    }

//    @Test
//    @Transactional
//    public void testFetechTypes() {
//        /*
//        1. LAZY
//        2. EAGER
//         */
//
//        Optional<Category> categoryOpt = categoryRepository.findById(1L);
//
//        if(categoryOpt.isPresent()){
//            Category category = categoryOpt.get();
//
//            System.out.println();
//
//            for(Product product: category.getProducts()){
//                System.out.println(product.getName());
//            }
//            //}
//        }
//
//    }
}


/*
LAzy = not doing everything beforehand

whenever you try to fetch category, it will not fetch products beforehand
only when you try to access products, then it will fetch


EAGER = it will be fetch beforehand

Join is default choice of eager fetch
2 select is better than join performed by JPA
actually tell jpa to not use join, instead set mode to select



Eager will always be eager doesn't really matter what mode it is
if the mode is join
if the mode is select => run 3 queries, it will run regarless

1. N+1 solutions

lazy  + select = N + 1 (batch size = 1)
1. get all categories
2. select * from products where category_id = ?  => N times


a. use lazy + sub select mode (2 queries)
b. use batch fetching(n(batch size) size in parent table)

if you specify batch size = n, we will see 1 + (N / n) queries



 */