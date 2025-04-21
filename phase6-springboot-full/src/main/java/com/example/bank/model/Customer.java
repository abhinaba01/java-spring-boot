package com.example.bank.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    private int id;
    private String name;

    public Customer() {}
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
}