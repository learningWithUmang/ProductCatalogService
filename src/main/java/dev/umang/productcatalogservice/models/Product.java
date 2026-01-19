package dev.umang.productcatalogservice.models;

import dev.umang.productcatalogservice.dtos.CategoryDTO;
import dev.umang.productcatalogservice.dtos.FakestoreProductDto;
import dev.umang.productcatalogservice.dtos.ProductDTO;
import lombok.Getter;
import lombok.Setter;


public class Product extends BaseModel {
    /*
    name                          : String
    description                  : String
    price                            : Double
    imageUrl                     : String
    category                      : Category
    */

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;

    public FakestoreProductDto convertToFakeStoreProduct(){
        FakestoreProductDto fakeStoreProductDto = new FakestoreProductDto();
        fakeStoreProductDto.setId(this.getId());
        fakeStoreProductDto.setTitle(this.getName());
        fakeStoreProductDto.setPrice(this.getPrice());
        fakeStoreProductDto.setDescription(this.getDescription());
        fakeStoreProductDto.setImage(this.getImageUrl());
        if(this.getCategory() != null) {
            fakeStoreProductDto.setCategory(this.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

    public ProductDTO convert(){
        ProductDTO productDto = new ProductDTO();
        productDto.setId(this.getId());
        productDto.setName(this.getName());
        productDto.setDescription(this.getDescription());
        productDto.setPrice(this.getPrice());
        productDto.setImageUrl(this.getImageUrl());
        if(this.getCategory() != null) {
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto.setName(this.getCategory().getName());
            categoryDto.setId(this.getCategory().getId());
            categoryDto.setDescription(this.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;

    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
