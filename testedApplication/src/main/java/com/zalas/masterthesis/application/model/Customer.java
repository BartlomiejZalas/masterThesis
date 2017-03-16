package com.zalas.masterthesis.application.model;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Transient
    private String dummyBigContentField;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
    }

    public String getDummyBigContentField() {
        try {
            return Resources.toString(Resources.getResource("/dummyText.txt"), Charsets.UTF_8);
        } catch (IOException e) {
            return "ERROR";
        }
    }
}
