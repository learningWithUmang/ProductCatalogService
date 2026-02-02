package dev.umang.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Entity
public class Category extends BaseModel{
    /*
    name                          : String
    description                  : String
    products                     : List<Product>
     */

    private String name;
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 15) // you are going to decide batch
    //size based on data analysis
    private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
/*
Product Category
  1 1
  M  1
  M : 1


  100K categories
  approx 1 category has 100 products
  100K categories + 10^7  products = ~1M records
  1 record = 100 bytes
    100M bytes = 100 MB


  Whenever you fetch collections, eager fetch is a very bad choice
  because lot of data at once will be brought to your working memory/RAM
  which may potentially crash your application.

  Never use eager in collections



  1. LAZY + SUB_SELECT = 2 queries
    a. bringing all categories
    b. bringing all products for each category (Can lead to memory explosion)
  2. use batch size (recommended)
    a. bring all categories in first query
    b. you'll run N/Batch size queries to bring products
      i.e. 100K/1500 = ~67 queries



     Try batch size with join
 */