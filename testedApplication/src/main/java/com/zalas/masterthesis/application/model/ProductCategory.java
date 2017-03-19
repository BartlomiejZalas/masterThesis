package com.zalas.masterthesis.application.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    private int id;
    private String name;
    private Set<Product> products;

    public ProductCategory() {
    }

    public ProductCategory(String name, Set<Product> products) {
        this.name = name;
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    public Set<Product> getProducts() {
        return products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
