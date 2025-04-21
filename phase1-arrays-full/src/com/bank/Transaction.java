package com.bank;

public class Transaction {
    private String description;
    private Account account;

    public Transaction(String description, Account account) {
        this.description = description;
        this.account = account;
    }

    public Account getAccount() { return account; }

    @Override
    public String toString() {
        return "Transaction{" + description + ", Account=" + account.getAccountNumber() + '}';
    }
}