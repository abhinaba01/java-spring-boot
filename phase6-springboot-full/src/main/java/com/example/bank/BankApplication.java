package com.example.bank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bank.model.*;
import com.example.bank.repository.*;
import com.example.bank.service.BankService;

@SpringBootApplication
public class BankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(BankService bank, CustomerRepository custRepo, AccountRepository accRepo) {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                Customer customer = new Customer(i, "Customer" + i);
                custRepo.save(customer);
                Account account = new Account(i * 1000, 1000.0, customer);
                accRepo.save(account);
                bank.deposit(account.getAccountNumber(), 0);
            }

            System.out.println("Initial Balance of 1000: " + bank.getBalance(1000));
            bank.deposit(1000, 500);
            bank.withdraw(1000, 200);
            bank.transfer(1000, 2000, 100);

            System.out.println("Final Balance of 1000: " + bank.getBalance(1000));
            System.out.println("Last 10 transactions of 1000:");
            bank.getLast10Transactions(1000).forEach(System.out::println);
        };
    }
}