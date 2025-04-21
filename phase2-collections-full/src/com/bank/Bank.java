package com.bank;

import java.util.*;

public class Bank {
    private Map<Integer, Customer> customers = new HashMap<>();
    private Map<Integer, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    public void initAccounts() {
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(i, "Customer" + i);
            customers.put(i, customer);
            Account account = new Account(i * 1000, 1000.0, customer);
            accounts.put(account.getAccountNumber(), account);
        }
    }

    public double getBalance(int accNo) {
        Account acc = accounts.get(accNo);
        if (acc == null) throw new RuntimeException("Invalid account");
        return acc.getBalance();
    }

    public void deposit(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc == null || amount <= 0) throw new RuntimeException("Invalid deposit");
        acc.setBalance(acc.getBalance() + amount);
        transactions.add(new Transaction("Deposit: " + amount, acc));
    }

    public void withdraw(int accNo, double amount) {
        Account acc = accounts.get(accNo);
        if (acc == null || amount <= 0 || acc.getBalance() < amount)
            throw new RuntimeException("Invalid withdraw");
        acc.setBalance(acc.getBalance() - amount);
        transactions.add(new Transaction("Withdraw: " + amount, acc));
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        Account from = accounts.get(fromAcc);
        Account to = accounts.get(toAcc);
        if (from == null || to == null || amount <= 0 || from.getBalance() < amount)
            throw new RuntimeException("Transfer failed");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        transactions.add(new Transaction("Transfer to " + toAcc + ": " + amount, from));
        transactions.add(new Transaction("Transfer from " + fromAcc + ": " + amount, to));
    }

    public void printLast10Transactions(int accNo) {
        int count = 0;
        ListIterator<Transaction> iter = transactions.listIterator(transactions.size());
        while (iter.hasPrevious() && count < 10) {
            Transaction tx = iter.previous();
            if (tx.getAccount().getAccountNumber() == accNo) {
                System.out.println(tx);
                count++;
            }
        }
    }
}