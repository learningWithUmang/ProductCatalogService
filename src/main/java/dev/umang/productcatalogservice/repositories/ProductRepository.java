package dev.umang.productcatalogservice.repositories;

import dev.umang.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

        /*
        JpaRepository provides the following methods:
        save(S entity) -> S
        findById(ID id) -> Optional<T>
        findAll() -> List<T>
        deleteById(ID id) -> void
        */

    Product save(Product p);

    List<Product> findProductByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findAllByOrderByPrice();


    /*
    Hibvermate queries
    Native sQL qiuero
     */

    //Example of names parameters
    @Query("SELECT p.description from Product p where p.id = :id")
    Product GetProductDescriptionWhereId(Long id, Long price);


    //Example of positional parameters
    @Query("SELECT p.description from Product p where p.id = ?1 and p.price = ?2")
    Product GetProductDescriptionWhereIdAndPrice(Long id, Long price);
/*
select * from products p where p.price >= minPrice and p.price <= maxPrice;
 */

}



/*
getProductById(id) -> SELECT * FROM Products p WHERE p.id = id;
 */