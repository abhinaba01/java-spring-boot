package com.bank.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id @GeneratedValue
    private int id;
    private String description;
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    private Account account;

    public Transaction() {}
    public Transaction(String description, Account account) {
        this.description = description;
        this.account = account;
    }

    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
}