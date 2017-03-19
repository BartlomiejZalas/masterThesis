package com.zalas.masterthesis.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    private int id;
    private String name;
    private ProductCategory productCategory;
    private Set<ProductOpinion> productOpinions;

    public Product() {
    }

    public Product(String name, ProductCategory productCategory, Set<ProductOpinion> productOpinions) {
        this.name = name;
        this.productCategory = productCategory;
        this.productOpinions = productOpinions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_category_id")
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public Set<ProductOpinion> getProductOpinions() {
        return productOpinions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductOpinions(Set<ProductOpinion> productOpinions) {
        this.productOpinions = productOpinions;
    }
}
