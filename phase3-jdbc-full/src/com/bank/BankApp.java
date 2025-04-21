package com.bank;

import java.sql.*;

public class BankApp {
    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.initDB();
        bank.initAccounts();

        System.out.println("Balance: " + bank.getBalance(1000));
        bank.deposit(1000, 500);
        bank.withdraw(1000, 200);
        bank.transfer(1000, 2000, 100);
        System.out.println("Final Balance: " + bank.getBalance(1000));
        bank.printLast10Transactions(1000);
    }
}