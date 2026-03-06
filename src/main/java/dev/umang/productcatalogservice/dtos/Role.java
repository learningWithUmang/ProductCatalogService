package dev.umang.productcatalogservice.dtos;
/*
Enums
constants with related values

Country

 */

import dev.umang.productcatalogservice.models.BaseModel;
import jakarta.persistence.Entity;

@Entity
public class Role extends BaseModel {
    private String name; //Mentor, Instructor, Admin, TA
    /*
    allowed permissions for this role
     */

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
