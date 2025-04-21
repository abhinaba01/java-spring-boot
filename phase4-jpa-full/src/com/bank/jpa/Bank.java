package com.bank.jpa;

import javax.persistence.*;
import java.util.List;

public class Bank {
    private EntityManager em;

    public Bank(EntityManager em) {
        this.em = em;
    }

    public void initAccounts() {
        for (int i = 1; i <= 10; i++) {
            Customer c = new Customer(i, "Customer" + i);
            Account a = new Account(i * 1000, 1000.0, c);
            em.persist(c);
            em.persist(a);
        }
    }

    public double getBalance(int accNo) {
        Account acc = em.find(Account.class, accNo);
        if (acc == null) throw new RuntimeException("Account not found");
        return acc.getBalance();
    }

    public void deposit(int accNo, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid deposit amount");
        Account acc = em.find(Account.class, accNo);
        if (acc == null) throw new RuntimeException("Account not found");
        acc.setBalance(acc.getBalance() + amount);
        em.persist(new Transaction("Deposit: " + amount, acc));
    }

    public void withdraw(int accNo, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid withdraw amount");
        Account acc = em.find(Account.class, accNo);
        if (acc == null || acc.getBalance() < amount)
            throw new RuntimeException("Insufficient funds or account not found");
        acc.setBalance(acc.getBalance() - amount);
        em.persist(new Transaction("Withdraw: " + amount, acc));
    }

    public void transfer(int fromAcc, int toAcc, double amount) {
        if (amount <= 0) throw new RuntimeException("Invalid transfer amount");
        Account from = em.find(Account.class, fromAcc);
        Account to = em.find(Account.class, toAcc);
        if (from == null || to == null || from.getBalance() < amount)
            throw new RuntimeException("Invalid accounts or insufficient funds");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        em.persist(new Transaction("Transfer to " + toAcc + ": " + amount, from));
        em.persist(new Transaction("Transfer from " + fromAcc + ": " + amount, to));
    }

    public void printLast10Transactions(int accNo) {
        TypedQuery<Transaction> query = em.createQuery(
            "SELECT t FROM Transaction t WHERE t.account.accountNumber = :accNo ORDER BY t.timestamp DESC", Transaction.class);
        query.setParameter("accNo", accNo);
        query.setMaxResults(10);
        List<Transaction> list = query.getResultList();
        for (Transaction t : list) System.out.println(t.getDescription());
    }
}