package dev.umang.productcatalogservice.repositories;

import dev.umang.productcatalogservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    Optional<Category> findById(Long id);

    @Override
    List<Category> findAll();
}

/*
select *
from products
where categ_id IN (select id from category where name = 'Electronics');
 */
