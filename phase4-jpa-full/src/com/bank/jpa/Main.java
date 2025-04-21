package com.bank.jpa;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankPU");
        EntityManager em = emf.createEntityManager();
        Bank bank = new Bank(em);

        em.getTransaction().begin();
        bank.initAccounts();
        em.getTransaction().commit();

        em.getTransaction().begin();
        System.out.println("Balance: " + bank.getBalance(1000));
        bank.deposit(1000, 500);
        bank.withdraw(1000, 200);
        bank.transfer(1000, 2000, 100);
        em.getTransaction().commit();

        System.out.println("Final Balance: " + bank.getBalance(1000));
        bank.printLast10Transactions(1000);

        em.close();
        emf.close();
    }
}