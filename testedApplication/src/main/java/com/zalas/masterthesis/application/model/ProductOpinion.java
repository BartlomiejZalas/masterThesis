package com.zalas.masterthesis.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "product_opinion")
public class ProductOpinion {

    private int id;
    private String content;
    private Date date;
    private Product product;

    public ProductOpinion() {
    }

    public ProductOpinion(String content, Date date, Product product) {
        this.content = content;
        this.date = date;
        this.product = product;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    @NotNull
    public Date getDate() {
        return date;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
