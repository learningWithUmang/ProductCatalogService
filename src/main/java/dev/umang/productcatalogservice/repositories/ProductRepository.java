package dev.umang.productcatalogservice.repositories;

import dev.umang.productcatalogservice.models.Product;
import io.micrometer.observation.annotation.ObservationKeyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

   @Override
    Product save(Product product);


   List<Product> findByPriceBetween(Double low, Double high);

   /*
   ordered by price
    */

    List<Product> findAllByOrderByPrice();

    @Query("SELECT p.description FROM Product p WHERE p.id = :id") //HQL
    String findDescriptionWhereIdIs(@Param("id") Long id);


    Page<Product> findByName(String name, Pageable pageable);
    /*
    select description
    from products
    where id = {x}
     */
}

/*
Pageable is an interface which has methods like getPageNo and
getPageSize which can be used,

PageRequest is one of Pageable implementations
 */
