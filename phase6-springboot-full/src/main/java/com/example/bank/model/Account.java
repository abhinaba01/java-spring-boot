package com.example.bank.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    private int accountNumber;
    private double balance;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    public Account() {}
    public Account(int accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}