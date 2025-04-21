package com.bank.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        BankService bank = new BankService(em);

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        bank.initAccounts();
        tx.commit();

        tx.begin();
        System.out.println("Balance: " + bank.getBalance(1000));
        bank.deposit(1000, 500);
        bank.withdraw(1000, 200);
        bank.transfer(1000, 2000, 100);
        tx.commit();

        System.out.println("Final Balance: " + bank.getBalance(1000));
        bank.printLast10Transactions(1000);

        em.close();
        context.close();
    }
}